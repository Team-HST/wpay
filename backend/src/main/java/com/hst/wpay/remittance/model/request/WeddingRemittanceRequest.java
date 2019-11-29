package com.hst.wpay.remittance.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lyoupyo@gmail.com
 */
@Data
public class WeddingRemittanceRequest {
	@ApiModelProperty(position = 1, name = "결혼 SEQ", example = "1")
	private long weddingSequence;
	@ApiModelProperty(position = 2, name = "혼주 사용자 SEQ", example = "1")
	private long hostSequence;
}
