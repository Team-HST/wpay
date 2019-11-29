package com.hst.wpay.mealticket.endpoint;


import com.hst.wpay.mealticket.model.entity.MealTicket;
import com.hst.wpay.mealticket.model.request.MealTicketIssueRequest;
import com.hst.wpay.mealticket.model.request.MealTicketUseRequest;
import com.hst.wpay.mealticket.service.MealTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("meal-tickets")
@Api( tags = "4. 식권 API", description = "식궙 발급/사용 기능을 제공합니다.")

public class MealticketController {
  
  private static final Logger logger = LoggerFactory.getLogger(MealticketController.class);
  
  @Autowired
  private MealTicketService mealticketService;
  
  @ApiOperation(value = "식권 발급", notes = "n개의 식권을 발급받습니다.")
  @PostMapping("issue")
  public ResponseEntity<MealTicket> issueMealTicket(@RequestBody MealTicketIssueRequest request) {
    MealTicket mealTicket = mealticketService.issueMealTicket(request);
    return ResponseEntity.ok(mealTicket);
  }
  
  @ApiOperation(value = "식권 사용", notes = "식권을 만료처리합니다.")
  @PutMapping("{mealTicketSequence}/use")
  public ResponseEntity<String> useMealTicket(@RequestBody MealTicketUseRequest request) {
    logger.info("식권 사용여부 확인");
    mealticketService.useMealTicket(request);
    return ResponseEntity.ok("식권 사용 처리 완료");
  }
  
}
