package com.hst.wpay.wedding.model.entity;

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

	@Column(name = "male_host_seq")
	private Long maleHostSeq;
	
	@Column(name = "female_host_seq")
	private Long femaleHostSeq;
	
	@Column(name = "wedding_dt")
	private LocalDateTime weddingDt;
	
	@Column(name = "reg_dt")
	private LocalDateTime regDt;
	
	@Column(name = "meal_ticket_price")
	private Long mealTicketPrice;
	
}
