package com.hst.wpay.common;

import com.hst.wpay.common.type.ResponseDescription;
import lombok.Builder;
import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
@Builder
public class DescribedResponse {

	private String responseCode;
	private int code;
	private String responseMessage;

}
