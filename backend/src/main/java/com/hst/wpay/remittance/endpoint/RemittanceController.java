package com.hst.wpay.remittance.endpoint;

import com.hst.wpay.remittance.model.request.TransferMoneyRequest;
import com.hst.wpay.remittance.service.RemittanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dlgusrb0808@gmail.com
 */
@RestController
@RequestMapping("remittance")
@Api( tags = "2. 축의금 API", description = "축의금 송금, 내역조회 기능 전반을 제공합니다.")
public class RemittanceController {

	@Autowired
	private RemittanceService remittanceService;

	@ApiOperation(value = "축의금 송금", notes = "축의금을 송금합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	@PostMapping("transfer")
	public ResponseEntity<String> transferMoney(@RequestBody TransferMoneyRequest request) {
		remittanceService.transferMoney(request);
		return ResponseEntity.ok("송금이 완료되었습니다.");
	}

}
