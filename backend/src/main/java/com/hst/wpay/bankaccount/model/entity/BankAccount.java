package com.hst.wpay.bankaccount.model.entity;

import com.hst.wpay.openbanking.model.response.OpenBankingAccountResponse;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hyungyu.lee@gmail.com
 */
@Entity
@Table(name = "BANK_ACCOUNT")
@Data
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long sequence;

	@Column(name = "masked_account_number")
	private String accountNumber;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "fintech_use_num")
	private String fintechUseNumber;

	@Column(name = "user_seq")
	private Long userSequence;

	@Column(name = "reg_dt")
	private LocalDateTime registeredAt;

	public static BankAccount createUserBankAccout(Long userSeq, OpenBankingAccountResponse response) {
		OpenBankingAccountResponse.Account targetAccount = response.getResList().get(0);
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAccountNumber(targetAccount.getAccountNumMasked());
		bankAccount.setFintechUseNumber(targetAccount.getFintechUseNum());
		bankAccount.setBankName(targetAccount.getBankName());
		bankAccount.setUserSequence(userSeq);
		bankAccount.setRegisteredAt(LocalDateTime.now());
		return bankAccount;
	}
}
