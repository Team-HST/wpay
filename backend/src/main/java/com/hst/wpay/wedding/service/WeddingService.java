package com.hst.wpay.wedding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hst.wpay.wedding.model.entity.Wedding;
import com.hst.wpay.wedding.model.request.CreateWeddingRequest;
import com.hst.wpay.wedding.model.response.CreateWeddingResponse;
import com.hst.wpay.wedding.repository.WeddingRepository;

/**
 * @author lyoupyo@gmail.com
 */
@Service
public class WeddingService {

	private static final Logger logger = LoggerFactory.getLogger(WeddingService.class);

	private WeddingRepository weddingRepository;

	/***
	 * 결혼 생성
	 * @param request 결혼 생성 요청
	 * @return 결혼 생성 결과
	 */
	public CreateWeddingResponse createWedding(CreateWeddingRequest request) {
		Wedding wedding = new Wedding();
		wedding.setMaleHostSeq(request.getMaleHostSeq());			// 남자혼주 일련번호
		wedding.setFemaleHostSeq(request.getFemaleHostSeq());		// 여자혼주 일련번호
		wedding.setWeddingDt(request.getWeddingDt());				// 결혼일시
		wedding.setRegDt(request.getRegDt());						// 등록일자
		wedding.setMealTicketPrice(request.getMealTicketPrice());	// 식권금액
		
		weddingRepository.save(wedding);

		return null;
	}
}
