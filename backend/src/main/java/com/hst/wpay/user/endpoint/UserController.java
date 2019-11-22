package com.hst.wpay.user.endpoint;

import com.hst.wpay.user.model.request.SigninRequest;
import com.hst.wpay.user.model.response.SigninResponse;
import com.hst.wpay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author dlgusrb0808@gmail.com
 */
@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("signin")
	public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request) {
		return ResponseEntity.ok(userService.signin(request));
	}

}
