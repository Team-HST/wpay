package com.hst.wpay.user.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
public class SignupRequest {
	@ApiModelProperty(position = 1, example = "사용할 ID")
	private String id;
	@ApiModelProperty(position = 2, example = "사용할 비밀번호")
	private String password;
	@ApiModelProperty(position = 3, example = "사용자 이름")
	private String name;

}
