package com.hst.wpay.bankaccount.service;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.bankaccount.repository.BankAccountRepository;
import com.hst.wpay.openbanking.model.response.OpenBankingAccountResponse;
import com.hst.wpay.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;

	@Autowired
	public BankAccountService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	public BankAccount getBankAccount(Long userSequence) {
		return bankAccountRepository.findByUserSequence(userSequence).get(0);
	}

	public void createUserBankAccount(Long userSeq, OpenBankingAccountResponse response) {
		bankAccountRepository.save(BankAccount.createUserBankAccout(userSeq, response));
	}
}
