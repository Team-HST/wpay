package com.hst.wpay.wedding.model.response;

import com.hst.wpay.wedding.model.entity.Wedding;

import lombok.Getter;

/**
 * @author lyoupyo@gmail.com
 */
@Getter
public class CreateWeddingResponse {

	private long sequence;

	/***
	 * 결혼 응답정보 생성
	 * @param wedding 결혼 엔티티
	 * @return 결혼 응답정보
	 */
	public static CreateWeddingResponse of(Wedding wedding) {
		CreateWeddingResponse response = new CreateWeddingResponse();
		response.sequence = wedding.getSequence();
		return response;
	}
}
