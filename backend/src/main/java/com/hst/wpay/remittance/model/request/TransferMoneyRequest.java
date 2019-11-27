package com.hst.wpay.remittance.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
public class TransferMoneyRequest {
	@ApiModelProperty(position = 1, name = "손님 사용자 SEQ", example = "1")
	private long guestSequence;
	@ApiModelProperty(position = 2, name = "혼주 사용자 SEQ", example = "2")
	private long hostSequence;
	@ApiModelProperty(position = 3, name = "결혼 SEQ", example = "5")
	private long weddingSequence;
	@ApiModelProperty(position = 4, name = "메모", example = "결혼을 축하드립니다.")
	private String comment;
	@ApiModelProperty(position = 5, name = "축의금 금액", example = "50000")
	private long amount;
}
