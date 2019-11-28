package com.hst.wpay.common.exception;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;

/**
 * @author dlgusrb0808@gmail.com
 */
public class DataNotFoundException extends ReportableException {
	DataNotFoundException(ResponseDescription description, String log) {
		super(description, log);
	}
}
