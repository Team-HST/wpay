package com.hst.wpay.user.endpoint;

import com.hst.wpay.openbanking.model.request.OpenBankingAuthorizedInformation;
import com.hst.wpay.user.model.request.SigninRequest;
import com.hst.wpay.user.model.request.SignupRequest;
import com.hst.wpay.user.model.response.SigninResponse;
import com.hst.wpay.user.model.response.SignupResponse;
import com.hst.wpay.user.model.response.UserResponse;
import com.hst.wpay.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * @author dlgusrb0808@gmail.com
 */
@Api( tags = "1. 사용자 API", description = "로그인, 회원가입, 사용자 계좌인증 기능을 제공합니다.")
@RestController
@RequestMapping("users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@ApiOperation(value = "로그인", notes = "로그인을 수행하고, API 이용에 사용가능한 인증토큰을 발급 받습니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@PostMapping("signin")
	public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request) {
		logger.info("로그인 요청");
		return ResponseEntity.ok(userService.signin(request));
	}

	@ApiOperation(value = "회원가입", notes = "회원가입을 진행합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@PostMapping("signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
		SignupResponse response = userService.signup(request);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "계좌인증 - 사용자인증", notes = "계좌인증을 위해 OpenBanking 사용자인증 페이지 url 을 발급합니다. 사용자가 인증을 완료하면 자동으로 계좌등록 절차가 진행됩니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping("{seq}/account-authentication")
	public ResponseEntity<String> accountAuthentication(@PathVariable Long seq) {
		return ResponseEntity.ok(userService.getOpenBankingAuthenticationProcessingUrl(seq));
	}

	@ApiOperation(value = "계좌인증 - 인증토큰 발급 및 계좌등록", notes = "사용자인증 API 수행 후 OpenBanking 측으로부터 호출되는 콜백 Handler. 이 단계에서 인증토큰과 계좌정보를 저장합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping("account-authentication-callback")
	public ResponseEntity<String> accountAuthenticationCallback(OpenBankingAuthorizedInformation request) {
		userService.processAssignUserOpenBankingAccount(request);
		return ResponseEntity.ok("<script type=\"text/javascript\">alert('계좌 인증처리가 완료되었습니다. 로그인 페이지로 이동합니다.');location.href = 'http://localhost:8080/login'</script>");
	}

	@ApiOperation(value = "사용자 목록 조회", notes = "전체 사용자 목록을 제공합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping
	public ResponseEntity<List<UserResponse>> getUsers() {
		List<UserResponse> userResponses = userService.getUsers();
		return ResponseEntity.ok(userResponses);
	}

	@ApiOperation(value = "사용자 조회", notes = "사용자 SEQ로 사용자를 제공합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping("{userSequence}")
	public ResponseEntity<UserResponse> getUserBySequence(@PathVariable long userSequence) {
		return ResponseEntity.ok(UserResponse.of(userService.getUser(userSequence)));
	}

	@ApiOperation(value = "사용자 조회", notes = "사용자 ID로 사용자를 제공합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping("ids/{userId}")
	public ResponseEntity<UserResponse> getUserByUserId(@PathVariable String userId) {
		return ResponseEntity.ok(UserResponse.of(userService.getUser(userId)));
	}

}
