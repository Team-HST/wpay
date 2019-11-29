package com.hst.wpay.wedding.service;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.exception.DataNotFoundException;
import com.hst.wpay.common.type.ResponseDescription;
import com.hst.wpay.mealticket.service.MealTicketService;
import com.hst.wpay.remittance.service.RemittanceService;
import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.user.service.UserService;
import com.hst.wpay.wedding.model.entity.Wedding;
import com.hst.wpay.wedding.model.entity.WeddingSettlement;
import com.hst.wpay.wedding.model.request.CreateWeddingRequest;
import com.hst.wpay.wedding.model.request.WeddingSettlementResponse;
import com.hst.wpay.wedding.model.response.CreateWeddingResponse;
import com.hst.wpay.wedding.model.response.WeddingResponse;
import com.hst.wpay.wedding.repository.WeddingRepository;
import com.hst.wpay.wedding.repository.WeddingSettlementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lyoupyo@gmail.com
 */
@Service
public class WeddingService {

	private static final Logger logger = LoggerFactory.getLogger(WeddingService.class);

	private final WeddingRepository weddingRepository;
	private final WeddingSettlementRepository weddingSettlementRepository;
	private final UserService userService;
	private final RemittanceService remittanceService;
	private final MealTicketService mealTicketService;

	@Autowired
	public WeddingService(WeddingRepository weddingRepository, WeddingSettlementRepository weddingSettlementRepository
			, UserService userService, RemittanceService remittanceService, MealTicketService mealTicketService) {
		this.weddingRepository = weddingRepository;
		this.weddingSettlementRepository = weddingSettlementRepository;
		this.userService = userService;
		this.remittanceService = remittanceService;
		this.mealTicketService = mealTicketService;
	}

	/***
	 * 결혼 생성
	 * @param request 결혼 생성 요청
	 * @return 결혼 생성 결과
	 */
	public CreateWeddingResponse createWedding(CreateWeddingRequest request) {
		User male = userService.getUser(request.getMaleHostSeq());
		User female = userService.getUser(request.getFemaleHostSeq());

		Wedding wedding = new Wedding();
		wedding.setMaleHost(male);
		wedding.setFemaleHost(female);
		wedding.setWeddingDt(request.getWeddingDt());
		wedding.setRegDt(LocalDateTime.now());
		wedding.setMealTicketPrice(request.getMealTicketPrice());
		
		weddingRepository.save(wedding);

		return CreateWeddingResponse.of(wedding);
	}

	/***
	 * 결혼정보 조회
	 * @param weddingSequence 결혼 SEQ
	 * @return 결혼정보
	 */
	public WeddingResponse getWedding(Long weddingSequence) {
		return WeddingResponse.of(getWeddingEntity(weddingSequence));
	}

	/***
	 * 결혼정보 조회
	 * @param weddingSequence 결혼 SEQ
	 * @return 결혼정보
	 */
	public Wedding getWeddingEntity(Long weddingSequence) {
		return weddingRepository.findById(weddingSequence).orElseThrow(() ->
				new ReportableException(ResponseDescription.COMMON_DATA_NOT_FOUND, String.format("결혼정보가 존재하지 않습니다. %d", weddingSequence)));
	}

	/***
	 * 결혼정보 목록 조회
	 * @return 결혼정보 목록
	 */
	public List<WeddingResponse> getWeddings() {
		return weddingRepository.findAll(Sort.by(Sort.Direction.DESC, "regDt")).stream().map(WeddingResponse::of).collect(Collectors.toList());
	}

	/***
	 * 결혼의 축의금/식대 현재 정산 정보 조회
	 * @param weddingSequence 결혼식 SEQ
	 * @return 정산정보
	 */
	public WeddingSettlementResponse getCurrentSettleWedding(long weddingSequence) {
		Wedding wedding = getWeddingEntity(weddingSequence);
		if (wedding.isSettled()) {
			return getWeddingSettlement(wedding);
		} else {
			return calculateWeddingMoney(wedding);
		}
	}

	private WeddingSettlementResponse calculateWeddingMoney(Wedding wedding) {
		long totalAmount = remittanceService.getTotalAmountByWedding(wedding.getSequence());
		int totalMealTicketCount = mealTicketService.getTotalIssuedMealTicketCount(wedding.getSequence());
		long totalMealPrice = totalMealTicketCount * wedding.getMealTicketPrice();
		long maleHostTotalAmount = remittanceService.getTotalAmountByHost(wedding.getMaleHost().getSequence(), wedding.getSequence());
		long femaleHostTotalAmount = remittanceService.getTotalAmountByHost(wedding.getFemaleHost().getSequence(), wedding.getSequence());

		logger.info("{}", createReceiptString(wedding, totalAmount, totalMealTicketCount, totalMealPrice,
				maleHostTotalAmount, femaleHostTotalAmount));

		return WeddingSettlementResponse.builder()
				.totalCelebrationAmount(totalAmount)
				.maleHostTotalCelebrationAmount(maleHostTotalAmount)
				.maleHostName(wedding.getMaleHost().getName())
				.femaleHostTotalCelebrationAmount(femaleHostTotalAmount)
				.femaleHostName(wedding.getFemaleHost().getName())
				.totalMealTicketCount(totalMealTicketCount)
				.totalMealPrice(totalMealPrice)
				.mealTicketPrice(wedding.getMealTicketPrice())
				.remainingAmount(totalAmount - totalMealPrice)
				.build();
	}
	/***
	 * 결혼식 정산
	 * @param weddingSequence
	 * @return
	 */
	public WeddingSettlementResponse settleWedding(long weddingSequence) {
		Wedding wedding = getWeddingEntity(weddingSequence);
		return processWeddingSettle(wedding);
	}

	// 결혼 정산결과 조회
	private WeddingSettlementResponse getWeddingSettlement(Wedding wedding) {
		logger.info("정산이 완료된 결혼정보");
		WeddingSettlement weddingSettlement = weddingSettlementRepository.findByWedding(wedding).orElseThrow(() ->
				new DataNotFoundException(ResponseDescription.COMMON_DATA_NOT_FOUND,
						String.format("결혼정산 정보가 존재하지 않습니다. weddingSeq: %d", wedding.getSequence())));
		return WeddingSettlement.unwrap(weddingSettlement);
	}

	// 결혼정산 진행
	private WeddingSettlementResponse processWeddingSettle(Wedding wedding) {
		logger.info("정산 시작");
		WeddingSettlementResponse calculatedWeddingSettlement = this.calculateWeddingMoney(wedding);

		logger.info("남자혼주 입금이체 완료");
		logger.info("여자혼주 입금이체 완료");

		saveWeddingSettlement(wedding, calculatedWeddingSettlement);
		updateWeddingOnSettleComplete(wedding);

		logger.info("정산 종료");
		return calculatedWeddingSettlement;
	}

	// 정산완료 처리
	private void updateWeddingOnSettleComplete(Wedding wedding) {
		wedding.setSettleYn("Y");
		weddingRepository.save(wedding);
	}

	// 결혼 정산정보 저장
	private void saveWeddingSettlement(Wedding wedding, WeddingSettlementResponse calculatedWeddingSettlement) {
		WeddingSettlement weddingSettlement = WeddingSettlement.wrapEntity(calculatedWeddingSettlement);
		weddingSettlement.setWedding(wedding);
		weddingSettlementRepository.save(weddingSettlement);
	}

	private String createReceiptString(Wedding wedding, long totalAmount, int totalMealTicketCount,
											  long totalMealPrice, long maleHostTotalAmount,
											  long femaleHostTotalAmount) {
		return new StringBuilder()
					.append("\r\n===================================").append("\r\n")
					.append("# 축의금").append("\r\n")
					.append(String.format("| 남자혼주(%s) 축의금 : %d", wedding.getMaleHost().getName(), maleHostTotalAmount)).append("\r\n")
					.append(String.format("| 여자혼주(%s) 축의금 : %d", wedding.getFemaleHost().getName(), femaleHostTotalAmount)).append("\r\n")
					.append(String.format("| 총 축의금 (남자혼주 축의금 + 여자혼주 축읨금  %d", totalAmount)).append("\r\n")
					.append("# 식대").append("\r\n")
					.append(String.format("| 총 발급식권                           %d", totalMealTicketCount)).append("\r\n")
					.append(String.format("| 식권 가격                             %d", wedding.getMealTicketPrice())).append("\r\n")
					.append(String.format("| 총 식대 (총 발급식권 * 식권 가격)         %d", totalMealPrice)).append("\r\n")
					.append("# 차액").append("\r\n")
					.append(String.format("| 차액 (총 축의금 - 총 식다)              %d", totalMealPrice)).append("\r\n")
					.append("===================================").toString();
	}


}
