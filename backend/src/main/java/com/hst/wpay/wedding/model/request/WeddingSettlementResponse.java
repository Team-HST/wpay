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
	private String maleHostName;
	private long femaleHostTotalCelebrationAmount;
	private String femaleHostName;
	private int totalMealTicketCount;
	private long totalMealPrice;
	private long remainingAmount;
	private long mealTicketPrice;
	private boolean isSettled;
}
