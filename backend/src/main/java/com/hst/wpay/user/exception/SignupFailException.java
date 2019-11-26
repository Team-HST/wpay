package com.hst.wpay.user.exception;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;

/**
 * @author dlgusrb0808@gmail.com
 */
public class SignupFailException extends ReportableException {
	public SignupFailException(ResponseDescription description, String log) {
		super(description, log);
	}
}
