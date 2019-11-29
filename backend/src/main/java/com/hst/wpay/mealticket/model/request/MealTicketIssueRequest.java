package com.hst.wpay.mealticket.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
public class MealTicketIssueRequest {
	@ApiModelProperty(position = 1, example = "발급할 식권 갯수")
	int mealTicketCount;
	@ApiModelProperty(position = 2, example = "손님 SEQ")
	long guestSeq;
	@ApiModelProperty(position = 3, example = "결혼 SEQ")
	long weddingSeq;

}
