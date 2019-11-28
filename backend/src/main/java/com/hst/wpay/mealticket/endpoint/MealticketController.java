package com.hst.wpay.mealticket.endpoint;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hst.wpay.mealticket.model.entity.MealTicket;
import com.hst.wpay.mealticket.service.MealTicketService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("meal-tickets")
@Api( tags = "4. 식권 API", description = "식궙 발급/사용 기능을 제공합니다.")
public class MealticketController {
  
  private static final Logger logger = LoggerFactory.getLogger(MealticketController.class);
  
  @Autowired
  private MealTicketService mealticketService;
  
  @ApiOperation(value = "식권 발급", notes = "n개의 식권을 발급받습니다.")
  @GetMapping("issue")
  public ResponseEntity<String> issueMealTicket(@RequestParam Integer mealTicketCount, Integer weddingSeq) {
    logger.info("티켓 발급 mealTicketCount: {}, weddingSeq: {}", mealTicketCount, weddingSeq);
    mealticketService.issueMealTicket(mealTicketCount, weddingSeq);
    return ResponseEntity.ok("티켓발급 완료");
  }
  
  @ApiOperation(value = "식권 사용", notes = "식권을 만료처리합니다.")
  @PutMapping("use")
  public ResponseEntity<String> useMealTicket(@RequestBody MealTicket request) {
    logger.info("식권 사용여부 확인");
    mealticketService.useMealTicket(request);
    return ResponseEntity.ok("식권 만료");
  }
  
}
