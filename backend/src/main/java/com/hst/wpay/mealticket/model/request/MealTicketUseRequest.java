package com.hst.wpay.mealticket.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
public class MealTicketUseRequest {
	@ApiModelProperty(position = 1, example = "식권 SEQ")
	Long mealTicketSequence;
	@ApiModelProperty(position = 2, example = "결혼 SEQ")
	Long weddingSequence;

}
