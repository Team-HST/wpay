package com.hst.wpay.wedding.model.entity;

import com.hst.wpay.user.model.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lyoupyo@gmail.com
 */
@Entity
@Table(name ="WEDDING")
@Data
public class Wedding {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long sequence;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "male_host_seq")
	private User maleHost;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "female_host_seq")
	private User femaleHost;
	
	@Column(name = "wedding_dt")
	private LocalDateTime weddingDt;
	
	@Column(name = "reg_dt")
	private LocalDateTime regDt;
	
	@Column(name = "meal_ticket_price")
	private Long mealTicketPrice;

	@Column(name = "settle_yn")
	private String settleYn;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "wedding")
	private WeddingSettlement weddingSettlement;

	public boolean isSettled() {
		return "Y".equals(this.settleYn);
	}
	
}
