package com.hst.wpay.remittance.service;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.bankaccount.service.BankAccountService;
import com.hst.wpay.openbanking.OpenBankingService;
import com.hst.wpay.openbanking.model.response.OpenBankingTransferWithdrawResponse;
import com.hst.wpay.remittance.model.entity.Remittance;
import com.hst.wpay.remittance.model.request.TransferMoneyRequest;
import com.hst.wpay.remittance.repository.RemittanceRepository;
import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class RemittanceService {

	private static final Logger logger = LoggerFactory.getLogger(RemittanceService.class);

	private final RemittanceRepository remittanceRepository;
	private final OpenBankingService openBankingService;
	private final BankAccountService bankAccountService;
	private final UserService userService;

	public RemittanceService(RemittanceRepository remittanceRepository, OpenBankingService openBankingService,
							 BankAccountService bankAccountService, UserService userService) {
		this.remittanceRepository = remittanceRepository;
		this.openBankingService = openBankingService;
		this.bankAccountService = bankAccountService;
		this.userService = userService;
	}

	/***
	 * 축의금 송금
	 * @param request 송금요청
	 */
	public void transferMoney(TransferMoneyRequest request) {
		User guest = userService.getUser(request.getGuestSequence());
		BankAccount guestBankAccount = bankAccountService.getBankAccount(request.getGuestSequence());

		OpenBankingTransferWithdrawResponse response = openBankingService.transfer(guest, guestBankAccount, request.getAmount());
		if (response.isSuccess()) {
			remittanceRepository.save(Remittance.of(request));
		} else {
			logger.error("사용자 계좌로 부터 출금에 실패하였습니다. 출금대상 사용자: {}, 실패사유: {}", request.getGuestSequence(),
					response.getRspMessage());
			throw new RuntimeException(String.format("사용자 계좌로부터 출금을 실패함. 실패사유: [%s]", response.getRspMessage()));
		}
	}
}
