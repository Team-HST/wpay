package com.hst.wpay.user.exception;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;

/**
 * @author dlgusrb0808@gmail.com
 */
public class SigninFailException extends ReportableException {
	/***
	 * 로그인 실패 Exception
	 * @param description 실패사유
	 */
	public SigninFailException(ResponseDescription description, String log) {
		super(description, log);
	}
}
