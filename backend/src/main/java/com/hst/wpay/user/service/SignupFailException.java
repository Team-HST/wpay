package com.hst.wpay.user.service;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;

/**
 * @author hyungyu.lee@nhn.com
 */
public class SignupFailException extends ReportableException {
	public SignupFailException(ResponseDescription description, String log) {
		super(description, log);
	}
}
