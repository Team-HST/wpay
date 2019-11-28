package com.hst.wpay.wedding.model.request;

import lombok.Builder;
import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Builder
@Getter
public class WeddingSettlementResponse {
	private long totalCelebrationAmount;
	private long maleHostTotalCelebrationAmount;
	private long femaleHostTotalCelebrationAmount;
	private int totalMealTicketCount;
	private long totalMealPrice;
	private long remainingAmount;
}
