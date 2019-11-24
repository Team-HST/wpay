package com.hst.wpay.user.endpoint;

import com.hst.wpay.user.model.request.SigninRequest;
import com.hst.wpay.user.model.request.SignupRequest;
import com.hst.wpay.user.model.response.SigninResponse;
import com.hst.wpay.user.model.response.SignupResponse;
import com.hst.wpay.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author dlgusrb0808@gmail.com
 */
@RestController
@RequestMapping("users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("signin")
	public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request) {
		logger.info("로그인 요청");
		return ResponseEntity.ok(userService.signin(request));
	}

	@PostMapping("signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
		SignupResponse response = userService.signup(request);
		return ResponseEntity.ok(response);
	}

}
