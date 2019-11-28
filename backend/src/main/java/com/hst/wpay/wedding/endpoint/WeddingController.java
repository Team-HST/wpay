package com.hst.wpay.wedding.endpoint;

import com.hst.wpay.wedding.model.request.WeddingSettlementResponse;
import com.hst.wpay.wedding.model.response.WeddingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hst.wpay.wedding.model.request.CreateWeddingRequest;
import com.hst.wpay.wedding.model.response.CreateWeddingResponse;
import com.hst.wpay.wedding.service.WeddingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * @author lyoupyo@gmail.com
 */
@Api( tags = "3. 결혼 API", description = "결혼 등록, 관리 기능을 제공합니다.")
@RestController
@RequestMapping("weddings")
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

	@ApiOperation(value = "결혼정보 목록 조회", notes = "결혼정보 목록을 조회합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping
	public ResponseEntity<List<WeddingResponse>> getWeddings() {
		return ResponseEntity.ok(weddingService.getWeddings());
	}

	@ApiOperation(value = "결혼정보 조회", notes = "결혼정보를 조회합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping("{seq}")
	public ResponseEntity<WeddingResponse> getWedding(@PathVariable long seq) {
		return ResponseEntity.ok(weddingService.getWedding(seq));
	}

	@ApiOperation(value = "결혼식 대금 정산", notes = "결혼식 축의금/식대을 정산합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@PostMapping("{weddingSequence}/settle")
	public ResponseEntity<WeddingSettlementResponse> settleWedding(@PathVariable long weddingSequence) {
		WeddingSettlementResponse response = weddingService.settleWedding(weddingSequence);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "결혼식 대금 조회", notes = "조회시점의 결혼식 축의금/식대를 조회합니다. 정산이 완료된 경우엔 정산결과에서 조회됩니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@GetMapping("{weddingSequence}/current-settlement")
	public ResponseEntity<WeddingSettlementResponse> getCurrentSettleWedding(@PathVariable long weddingSequence) {
		return ResponseEntity.ok(weddingService.getCurrentSettleWedding(weddingSequence));
	}

}
