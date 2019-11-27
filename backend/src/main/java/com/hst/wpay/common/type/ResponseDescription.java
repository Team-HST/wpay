package com.hst.wpay.common.type;

import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
public enum ResponseDescription {
	USER_NOT_FOUND(1900, "[사용자] 사용자가 존재하지 않습니다."),
	SIGNIN_SUCCESS(1010, "[사용자] 로그인 성공"),
	SIGNIN_FAIL_USER_NOT_FOUND(1011, "[사용자] 로그인 실패 - 사용자를 찾을 수 없습니다."),
	SIGNIN_FAIL_INVALID_PASSWORD(1012, "[사용자] 로그인 실패 - 비밀번호가 잘못됐습니다."),
	SIGNUP_SUCCESS(1020, "[사용자] 회원가입 성공"),
	SIGNUP_SUCCESS_USER_ALREADY_EXIST(1021, "[사용자] 회원가입 실패 - 이미 존재하는 사용자입니다.")
	;

	private int code;
	private String message;

	ResponseDescription(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
