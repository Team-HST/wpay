package com.hst.wpay.bankaccount.repository;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	List<BankAccount> findByUserSequence(Long userSequence);
}
