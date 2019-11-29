package com.hst.wpay.remittance.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hst.wpay.remittance.model.entity.Remittance;
import com.hst.wpay.user.model.response.UserResponse;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
public class RemittanceResponse {
	private long sequence;
	private UserResponse guest;
	private UserResponse host;
	private String comment;
	private long amount;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime remittanceAt;

	public static RemittanceResponse of(Remittance remittance) {
		RemittanceResponse response = new RemittanceResponse();
		response.sequence = remittance.getSequence();
		response.guest = UserResponse.of(remittance.getGuest());
		response.host = UserResponse.of(remittance.getHost());
		response.comment = remittance.getComment();
		response.amount = remittance.getAmount();
		response.remittanceAt = remittance.getRemittanceAt();
		return response;
	}
}
