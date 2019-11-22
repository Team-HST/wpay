package com.hst.wpay.user.service;

import com.hst.wpay.jwt.service.JwtService;
import com.hst.wpay.user.exception.SigninFailException;
import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class UserService implements UserDetailsService {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String seq) throws UsernameNotFoundException {
		return userRepository.findById(Long.valueOf(seq))
				.orElseThrow(() -> new UsernameNotFoundException(String.format("사용자 정보가 존재하지 않습니다. seq: %s", seq)));
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
	 * @param id 로그인 아이디
	 * @param password 비밀번호
	 */
	public void signin(String id, String password) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		if (passwordEncoder.matches(password, user.getPassword())) {
			throw new SigninFailException("");
		}
	}
}
