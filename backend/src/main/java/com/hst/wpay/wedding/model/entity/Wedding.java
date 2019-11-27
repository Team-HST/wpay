package com.hst.wpay.wedding.model.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

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
	private Date WeddingDt;
	
	@Column(name = "reg_dt")
	private Date RegDt;
	
	@Column(name = "meal_ticket_price")
	private Long MealTicketPrice;
	
}
