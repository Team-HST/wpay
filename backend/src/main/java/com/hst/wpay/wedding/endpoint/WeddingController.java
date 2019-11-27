package com.hst.wpay.wedding.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hst.wpay.wedding.model.request.CreateWeddingRequest;
import com.hst.wpay.wedding.model.response.CreateWeddingResponse;
import com.hst.wpay.wedding.service.WeddingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author lyoupyo@gmail.com
 */
@Api( tags = "2. 결혼 API", description = "결혼 등록, 관리 기능을 제공합니다.")
@RestController
@RequestMapping("wedding")
public class WeddingController {

	private static final Logger logger = LoggerFactory.getLogger(WeddingController.class);

	@Autowired
	private WeddingService weddingService;
	
	@ApiOperation(value = "결혼매칭", notes = "결혼정보를 생성합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@PostMapping("create")
	public ResponseEntity<CreateWeddingResponse> createWedding(@RequestBody CreateWeddingRequest request) {
		return ResponseEntity.ok(weddingService.createWedding(request));
	}
	
}
