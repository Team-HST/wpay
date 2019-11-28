package com.hst.wpay.common;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
@Builder
public class DescribedResponse {

	private String responseCode;
	private int code;
	private String responseMessage;
	private Map<String, Object> extraData;

}
