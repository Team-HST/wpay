package com.hst.wpay.common;

import com.hst.wpay.common.type.ResponseDescription;
import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
public class ReportableException extends RuntimeException {

	private ResponseDescription description;
	private String log;

	public ReportableException(ResponseDescription description, String log) {
		super(description.getMessage());
		this.description = description;
		this.log = log;
	}

	public ResponseDescription getDescription() {
		return description;
	}

	public String getLog() {
		return log;
	}
}
