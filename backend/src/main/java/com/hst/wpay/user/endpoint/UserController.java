package com.hst.wpay.user.endpoint;

import com.hst.wpay.user.model.request.SigninRequest;
import com.hst.wpay.user.model.request.SignupRequest;
import com.hst.wpay.user.model.response.SigninResponse;
import com.hst.wpay.user.model.response.SignupResponse;
import com.hst.wpay.user.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
