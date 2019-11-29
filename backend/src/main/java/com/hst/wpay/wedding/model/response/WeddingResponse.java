package com.hst.wpay.wedding.model.response;

import com.hst.wpay.user.model.response.UserResponse;
import com.hst.wpay.wedding.model.entity.Wedding;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
public class WeddingResponse {

	private long sequence;
	private UserResponse maleHost;
	private UserResponse femaleHost;
	private LocalDateTime weddingDate;
	private LocalDateTime registeredAt;
	private long mealTicketPrice;
	private boolean isSettled;

	public static WeddingResponse of(Wedding wedding) {
		WeddingResponse response = new WeddingResponse();
		response.sequence = wedding.getSequence();
		response.maleHost = UserResponse.of(wedding.getMaleHost());
		response.femaleHost = UserResponse.of(wedding.getFemaleHost());
		response.weddingDate = wedding.getWeddingDt();
		response.registeredAt = wedding.getRegDt();
		response.mealTicketPrice = wedding.getMealTicketPrice();
		response.isSettled = wedding.isSettled();
		return response;
	}

}
