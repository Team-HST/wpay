package com.hst.wpay.wedding.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lyoupyo@gmail.com
 */
@Data
public class CreateWeddingRequest {
	
	@ApiModelProperty(position = 1, example = "남자 혼주 일련번호")
	private Long maleHostSeq;

	@ApiModelProperty(position = 2, example = "여자 혼주 일련번호")
	private Long femaleHostSeq;
	
	@ApiModelProperty(position = 3, example = "결혼일시")
	private LocalDateTime weddingDt;
	
	@ApiModelProperty(position = 4, example = "식권금액")
	private Long mealTicketPrice;
}
