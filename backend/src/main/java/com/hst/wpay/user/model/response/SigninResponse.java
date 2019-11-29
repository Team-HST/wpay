package com.hst.wpay.user.model.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Builder
@Getter
public class SigninResponse {

	private String token;

	private UserResponse user;
	
	private Long weddingSequence;

}
