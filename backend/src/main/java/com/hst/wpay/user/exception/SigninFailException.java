package com.hst.wpay.user.exception;

/**
 * @author dlgusrb0808@gmail.com
 */
public class SigninFailException extends RuntimeException {
	/***
	 * 로그인 실패 Exception
	 * @param reason 실패사유
	 */
	SigninFailException(String reason) {
		super(reason);
	}
}
