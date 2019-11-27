package com.hst.wpay.wedding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hst.wpay.wedding.model.entity.Wedding;
import com.hst.wpay.wedding.model.request.CreateWeddingRequest;
import com.hst.wpay.wedding.model.response.CreateWeddingResponse;
import com.hst.wpay.wedding.repository.WeddingRepository;

import java.time.LocalDateTime;

/**
 * @author lyoupyo@gmail.com
 */
@Service
public class WeddingService {

	private static final Logger logger = LoggerFactory.getLogger(WeddingService.class);

	private final WeddingRepository weddingRepository;

	@Autowired
	public WeddingService(WeddingRepository weddingRepository) {
		this.weddingRepository = weddingRepository;
	}

	/***
	 * 결혼 생성
	 * @param request 결혼 생성 요청
	 * @return 결혼 생성 결과
	 */
	public CreateWeddingResponse createWedding(CreateWeddingRequest request) {
		Wedding wedding = new Wedding();
		wedding.setMaleHostSeq(request.getMaleHostSeq());
		wedding.setFemaleHostSeq(request.getFemaleHostSeq());
		wedding.setWeddingDt(request.getWeddingDt());
		wedding.setRegDt(LocalDateTime.now());
		wedding.setMealTicketPrice(request.getMealTicketPrice());
		
		weddingRepository.save(wedding);

		return CreateWeddingResponse.of(wedding);
	}
}
