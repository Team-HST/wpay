package com.hst.wpay.remittance.model.entity;

import com.hst.wpay.remittance.model.request.TransferMoneyRequest;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author dlgusrb0808@gmail.com
 */
@Entity
@Table(name = "REMITTANCE")
@Getter
public class Remittance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long sequence;

	@Column(name = "guest_seq")
	private Long guestSequence;

	@Column(name = "host_seq")
	private Long hostSequence;

	@Column(name = "wedding_seq")
	private Long weddingSequence;

	@Column
	private String comment;

	@Column
	private Long amount;

	@Column(name = "remittance_dt")
	private LocalDateTime remittanceAt;

	public static Remittance of(TransferMoneyRequest request) {
		Remittance remittance = new Remittance();
		remittance.guestSequence = request.getGuestSequence();
		remittance.hostSequence = request.getHostSequence();
		remittance.amount = request.getAmount();
		remittance.comment = request.getComment();
		remittance.remittanceAt = LocalDateTime.now();
		remittance.weddingSequence = request.getWeddingSequence();
		return remittance;
	}
}
