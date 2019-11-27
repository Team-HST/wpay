package com.hst.wpay.wedding.service;

import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.user.model.response.UserResponse;
import com.hst.wpay.user.service.UserService;
import com.hst.wpay.wedding.model.response.WeddingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hst.wpay.wedding.model.entity.Wedding;
import com.hst.wpay.wedding.model.request.CreateWeddingRequest;
import com.hst.wpay.wedding.model.response.CreateWeddingResponse;
import com.hst.wpay.wedding.repository.WeddingRepository;

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
	private final UserService userService;

	@Autowired
	public WeddingService(WeddingRepository weddingRepository, UserService userService) {
		this.weddingRepository = weddingRepository;
		this.userService = userService;
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
	 * @param seq 결혼 SEQ
	 * @return 결혼정보
	 */
	public WeddingResponse getWedding(long seq) {
		Wedding wedding = weddingRepository.findById(seq)
				.orElseThrow(() -> new RuntimeException(String.format("결혼정보가 존재하지 않습니다. %d", seq)));
		return WeddingResponse.of(wedding);
	}

	/***
	 * 결혼정보 목록 조회
	 * @return 결혼정보 목록
	 */
	public List<WeddingResponse> getWeddings() {
		return weddingRepository.findAll().stream().map(WeddingResponse::of).collect(Collectors.toList());
	}

}
