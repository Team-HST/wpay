package com.hst.wpay.remittance.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.bankaccount.service.BankAccountService;
import com.hst.wpay.openbanking.OpenBankingService;
import com.hst.wpay.openbanking.model.response.OpenBankingTransferWithdrawResponse;
import com.hst.wpay.remittance.model.entity.Remittance;
import com.hst.wpay.remittance.model.request.TransferMoneyRequest;
import com.hst.wpay.remittance.model.request.WeddingRemittanceRequest;
import com.hst.wpay.remittance.model.response.RemittanceResponse;
import com.hst.wpay.remittance.repository.RemittanceRepository;
import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.user.service.UserService;

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

		logger.info("손님 계좌 출금이체 요청. [guestSeq: {}, fintechUseNum: {}]", guest.getSequence(), guestBankAccount.getFintechUseNumber());

		OpenBankingTransferWithdrawResponse response = openBankingService.transfer(guest, guestBankAccount, request.getAmount());
		if (response.isSuccess()) {
			User host = userService.getUser(request.getHostSequence());
			Remittance remittance = Remittance.of(request);
			remittance.setTransferInfo(guest, host);

			logger.info("출금이체 완료, 축의금 송금이력 저장. [hostSeq: {}]", host.getSequence());

			remittanceRepository.save(remittance);
		} else {
			logger.error("사용자 계좌로 부터 출금에 실패하였습니다. 출금대상 사용자: {}, 실패사유: {}", request.getGuestSequence(),
					response.getRspMessage());
			throw new RuntimeException(String.format("사용자 계좌로부터 출금을 실패함. 실패사유: [%s]", response.getRspMessage()));
		}
	}

	/***
	 * 사용자 축의금 지출내역
	 * @param userSequence 사용자 SEQ
	 * @return 사용자 축의금 지출내역
	 */
	public List<RemittanceResponse> getUserRemittanceHistories(Long userSequence, Integer month) {
		// 날짜 포맷 세팅
		GregorianCalendar today = new GregorianCalendar();
		String year = Integer.toString(today.get(today.YEAR));
		GregorianCalendar cld = new GregorianCalendar(today.get(today.YEAR), month-1, 1);
		String maxday = Integer.toString(cld.getActualMaximum((Calendar.DAY_OF_MONTH)));

		String compareDtStr = year+month+maxday;
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		LocalDateTime compareDt = LocalDateTime.parse(compareDtStr, dateFormatter);
		
		return remittanceRepository.findByGuest_Sequence(userSequence, compareDt).stream()
				.map(RemittanceResponse::of).collect(Collectors.toList());
	}

	/***
	 * 결혼 축의금 송금내역
	 * @param weddingSequence 결혼 SEQ
	 * @return 결혼 축의금 송금내역
	 */
	public List<RemittanceResponse> getWeddingRemittanceHistories(WeddingRemittanceRequest weddingRemittanceRequest) {
		return remittanceRepository.findByWeddingSequence
									(weddingRemittanceRequest.getWeddingSequence()
									,weddingRemittanceRequest.getHostSequence()).stream()
				.map(RemittanceResponse::of).collect(Collectors.toList());
	}

	/***
	 * 결혼 총 축의금 계산
	 * @param weddingSequence 결혼 SEQ
	 * @return 총 축의금
	 */
	public long getTotalAmountByWedding(Long weddingSequence) {
		return Optional.fromNullable(remittanceRepository.findTotalAmountByWeddingSequence(weddingSequence)).or(0L);
	}

	/***
	 * 혼주의 총 축의금 계산
	 * @param hostSequence 혼주 SEQ
	 * @return 총 축의금
	 */
	public long getTotalAmountByHost(Long hostSequence, Long weddingSequence) {
		return Optional.fromNullable(remittanceRepository.findTotalAmountByHostSequence(hostSequence, weddingSequence)).or(0L);
	}	
}
