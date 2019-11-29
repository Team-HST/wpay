package com.hst.wpay.wedding.model.entity;

import com.hst.wpay.wedding.model.request.WeddingSettlementResponse;
import lombok.Data;

import javax.persistence.*;

/**
 * @author dlgusrb0808@gmail.com
 */
@Entity
@Table(name ="WEDDING_SETTLEMENT")
@Data
public class WeddingSettlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long sequence;

	@Column(name = "total_celebration_price")
	private Long totalCelebrationAmount;

	@Column(name = "male_host_total_celebration_price")
	private Long maleTotalCelebrationAmount;

	@Column(name = "female_host_total_celebration_price")
	private Long femaleTotalCelebrationAmount;

	@Column(name = "total_meal_cnt")
	private Integer totalMealTicketCount;

	@Column(name = "total_meal_price")
	private Long totalMealPrice;

	@Column(name = "diff_price")
	private Long remainingAmount;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wedding_seq")
	private Wedding wedding;

	public static WeddingSettlement wrapEntity(WeddingSettlementResponse calculatedWeddingSettlement) {
		WeddingSettlement weddingSettlement = new WeddingSettlement();
		weddingSettlement.totalCelebrationAmount = calculatedWeddingSettlement.getTotalCelebrationAmount();
		weddingSettlement.maleTotalCelebrationAmount = calculatedWeddingSettlement.getMaleHostTotalCelebrationAmount();
		weddingSettlement.femaleTotalCelebrationAmount = calculatedWeddingSettlement.getFemaleHostTotalCelebrationAmount();
		weddingSettlement.totalMealTicketCount = calculatedWeddingSettlement.getTotalMealTicketCount();
		weddingSettlement.totalMealPrice = calculatedWeddingSettlement.getTotalMealPrice();
		weddingSettlement.remainingAmount = calculatedWeddingSettlement.getRemainingAmount();
		return weddingSettlement;
	}

	public static WeddingSettlementResponse unwrap(WeddingSettlement weddingSettlement) {
		return WeddingSettlementResponse.builder()
				.totalCelebrationAmount(weddingSettlement.getTotalCelebrationAmount())
				.maleHostTotalCelebrationAmount(weddingSettlement.getMaleTotalCelebrationAmount())
				.maleHostName(weddingSettlement.getWedding().getMaleHost().getName())
				.femaleHostTotalCelebrationAmount(weddingSettlement.getFemaleTotalCelebrationAmount())
				.femaleHostName(weddingSettlement.getWedding().getFemaleHost().getName())
				.totalMealTicketCount(weddingSettlement.getTotalMealTicketCount())
				.totalMealPrice(weddingSettlement.getTotalMealPrice())
				.mealTicketPrice(weddingSettlement.getWedding().getMealTicketPrice())
				.remainingAmount(weddingSettlement.getRemainingAmount())
				.isSettled(true)
				.build();
	}

}
