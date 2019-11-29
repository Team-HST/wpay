package com.hst.wpay.remittance.model.entity;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.remittance.model.request.TransferMoneyRequest;
import com.hst.wpay.user.model.entity.User;
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "guest_seq")
	private User guest;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "host_seq")
	private User host;
	
	@Column(name = "wedding_seq")
	private Long weddingSequence;

	@Column
	private String comment;

	@Column
	private Long amount;

	@Column(name = "remittance_dt")
	private LocalDateTime remittanceAt;

	public void setTransferInfo(User guest, User host) {
		this.guest = guest;
		this.host = host;
	}

	public static Remittance of(TransferMoneyRequest request) {
		Remittance remittance = new Remittance();
		remittance.amount = request.getAmount();
		remittance.comment = request.getComment();
		remittance.remittanceAt = LocalDateTime.now();
		remittance.weddingSequence = request.getWeddingSequence();
		return remittance;
	}
}
