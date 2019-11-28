package com.hst.wpay.common.type;

import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
public enum ResponseDescription {
	SIGNIN_SUCCESS(100, "[로그인] 성공"),
	SIGNIN_FAIL_USER_NOT_FOUND(101, "[로그인] 실패 - 사용자를 찾을 수 없습니다."),
	SIGNIN_FAIL_INVALID_PASSWORD(102, "[로그인] 실패 - 비밀번호가 잘못됐습니다."),
	SIGNUP_SUCCESS(200, "[회원가입] 성공"),
	SIGNUP_SUCCESS_USER_ALREADY_EXIST(201, "[회원가입] 실패 - 이미 존재하는 사용자입니다."),

	MEALTICKET_EXPIRATION(401, "[식권] 이미 사용 또는 기간이 지나 만료된 사용할 수 없는 식권입니다."),

	COMMON_DATA_NOT_FOUND(900, "데이터가 존재하지 않습니다.")
	;

	private int code;
	private String message;

	ResponseDescription(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
