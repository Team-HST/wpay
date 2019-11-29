package com.hst.wpay.common;

import com.hst.wpay.common.type.ResponseDescription;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dlgusrb0808@gmail.com
 */
public class ReportableException extends RuntimeException {

	private final ResponseDescription description;
	private final String log;
	private Map<String, Object> extraData;

	public ReportableException(ResponseDescription description) {
		super(description.getMessage());
		this.description = description;
		this.log = description.getMessage();
	}

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

	public void addExtraData(String name, Object value) {
		if (this.extraData == null) {
			this.extraData = new HashMap<>();
		}
		this.extraData.put(name, value);
	}

	public Map<String, Object> getExtraData() {
		return extraData;
	}
}
