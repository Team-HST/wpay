package com.hst.wpay.wedding.model.request;

import java.sql.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
	private Date WeddingDt;
	
	@ApiModelProperty(position = 4, example = "등록일자")
	private Date RegDt;
	
	@ApiModelProperty(position = 5, example = "식권금액")
	private Long mealTicketPrice;
}
