package com.hst.wpay.user.service;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.bankaccount.service.BankAccountService;
import com.hst.wpay.common.type.ResponseDescription;
import com.hst.wpay.jwt.service.JwtService;
import com.hst.wpay.openbanking.OpenBankingService;
import com.hst.wpay.openbanking.model.response.OpenBankingAccountResponse;
import com.hst.wpay.openbanking.model.response.OpenBankingOAuthTokenResponse;
import com.hst.wpay.openbanking.model.request.OpenBankingAuthorizedInformation;
import com.hst.wpay.user.exception.SigninFailException;
import com.hst.wpay.user.exception.SignupFailException;
import com.hst.wpay.user.exception.UserNotFoundException;
import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.user.model.request.SigninRequest;
import com.hst.wpay.user.model.request.SignupRequest;
import com.hst.wpay.user.model.response.SigninResponse;
import com.hst.wpay.user.model.response.SignupResponse;
import com.hst.wpay.user.model.response.UserResponse;
import com.hst.wpay.user.model.type.Role;
import com.hst.wpay.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class UserService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final JwtService jwtService;
	private final OpenBankingService openBankingService;
	private final BankAccountService bankAccountService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
					   OpenBankingService openBankingService, BankAccountService bankAccountService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.openBankingService = openBankingService;
		this.bankAccountService = bankAccountService;
	}

	@Override
	public UserDetails loadUserByUsername(String seq) throws UsernameNotFoundException {
		return userRepository.findById(Long.valueOf(seq)).orElseThrow(() -> new UsernameNotFoundException(String.format("사용자 정보가 존재하지 않습니다. seq: %s", seq)));
	}

	/***
	 * 인증토큰을 파싱해 인증정보 획득
	 * @param token 인증토큰
	 * @return 인증정보
	 */
	public Authentication getAuthentication(String token) {
		Jws<Claims> jwt = jwtService.parse(token);
		UserDetails userDetails = loadUserByUsername(jwt.getBody().getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
	}

	/***
	 * 로그인
	 * @param request 로그인 요청
	 * @return 로그인 결과
	 */
	public SigninResponse signin(SigninRequest request) {
		Optional<User> optionalUser = userRepository.findById(request.getId());
		if (!optionalUser.isPresent()) {
			throw new SigninFailException(ResponseDescription.SIGNIN_FAIL_USER_NOT_FOUND,
					String.format("사용자를 찾을 수 없습니다. id: %s", request.getId()));
		}
		if (!passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
			throw new SigninFailException(ResponseDescription.SIGNIN_FAIL_INVALID_PASSWORD, "비밀번호가 다릅니다.");
		}
		User user = optionalUser.get();
		if (!user.isBankAccountAuthorized()) {
			SigninFailException e = new SigninFailException(ResponseDescription.SIGNIN_FAIL_BANK_ACCOUNT_UNAUTHRIZED, "비밀번호가 다릅니다.");
			e.addExtraData("userSequence", user.getSequence());
			throw e;
		}

		UserResponse userResponse = UserResponse.of(optionalUser.get());
		String token = jwtService.createToken(optionalUser.get().getSequence(), userResponse);
		return SigninResponse.builder().token(token).user(userResponse).build();
	}

	/***
	 * 회원가입 요청
	 * @param request 가입요청
	 * @return 가입 결과
	 */
	public SignupResponse signup(SignupRequest request) {
		Optional<User> opUser = userRepository.findById(request.getId());
		if (opUser.isPresent()) {
			throw new SignupFailException(ResponseDescription.SIGNUP_SUCCESS_USER_ALREADY_EXIST,
					String.format("이미 존재하는 ID 입니다. %s", request.getId()));
		}

		User user = new User();
		user.setId(request.getId());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setName(request.getName());
		user.setRoles(Collections.singleton(Role.USER.getAuthority()));
		user.setBankAccountAuthorizedYn("N");
		userRepository.save(user);

		return SignupResponse.builder()
				.userResponse(UserResponse.of(user))
				.build();
	}

	/***
	 * 계좌등록 1. OpenBanking 사용자인증
	 * @param seq 인증대상 사용자 seq
	 * @return 사용자인증 URL
	 */
	public String getOpenBankingAuthenticationProcessingUrl(Long seq) {
		logger.info("계좌인증 1단계 - OpenBanking 사용자인증");

		User user = getUser(seq);

		logger.info("인증대상: [{}/{}/{}]", user.getSequence(), user.getId(), user.getName());

		return openBankingService.getAuthenticationProcessingUrl(seq);
	}

	/***
	 * 계좌등록 2. OpenBanking 계좌 조회 및 등록
	 * @param authorizedInformation 사용자인증 정보
	 */
	@Transactional
	public void processAssignUserOpenBankingAccount(OpenBankingAuthorizedInformation authorizedInformation) {
		User user = getUser(Long.valueOf(authorizedInformation.getClient_info()));
		assignUserOpenBankingOAuthToken(user, authorizedInformation);
		assignUserBankAccount(user, authorizedInformation);
	}

	/***
	 * OpenBanking OAuth 토큰 발행하여 사용자에게 설정
	 */
	private void assignUserOpenBankingOAuthToken(User user, OpenBankingAuthorizedInformation authorizedInformation) {
		logger.info("계좌인증 2단계 - OpenBanking OAuth 토큰 발행");
		logger.info("OAuth 인증토큰 발급대상: [{}/{}/{}]", user.getSequence(), user.getId(), user.getName());

		OpenBankingOAuthTokenResponse response = openBankingService.issueOauthToken(authorizedInformation.getCode());
		logger.info("OAuth 인증토큰 : {}", response);
		user.setOpenBankingTokenInfo(response);
		userRepository.save(user);
	}

	/***
	 * 사용자 계좌정보를 조회하여 사용자에게 설정
	 */
	private void assignUserBankAccount(User user, OpenBankingAuthorizedInformation request) {
		logger.info("계좌인증 3단계 - 인증된 계좌정보 등록");

		OpenBankingAccountResponse response = openBankingService.getBankAccount(user.getBankAccessToken(), user.getBankUserSequenceNumber());
		bankAccountService.createUserBankAccount(user, response);
	}

	/***
	 * 사용자 조회
	 * @param userId 사용자 아이디
	 * @return 사용자 정보
	 */
	public User getUser(String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(ResponseDescription.USER_NOT_FOUND,
					String.format("사용자가 존재하지 않습니다. id: %s", userId));
		}
		return user.get();
	}

	/***
	 * 사용자 조회
	 * @param seq 사용자 seq
	 * @return 사용자 정보
	 */
	public User getUser(Long seq) {
		Optional<User> user = userRepository.findById(seq);
		if (!user.isPresent()) {
			throw new UserNotFoundException(ResponseDescription.USER_NOT_FOUND,
					String.format("사용자가 존재하지 않습니다. seq: %d", seq));
		}
		return user.get();
	}

	/***
	 * 사용자 목록 조회
	 * @return 사용자 목록
	 */
	public List<UserResponse> getUsers() {
		return userRepository.findAll().stream().map(UserResponse::of).collect(Collectors.toList());
	}

}
