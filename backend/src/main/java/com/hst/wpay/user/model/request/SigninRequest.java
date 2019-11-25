package com.hst.wpay.user.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
public class SigninRequest {

	@ApiModelProperty(position = 1, example = "로그인 ID")
	private String id;

	@ApiModelProperty(position = 2, example = "로그인 비밀번호")
	private String password;

}
