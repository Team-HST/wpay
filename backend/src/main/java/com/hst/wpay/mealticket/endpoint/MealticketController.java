package com.hst.wpay.mealticket.endpoint;

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
import com.hst.wpay.mealticket.service.MealticketService;
import com.hst.wpay.user.endpoint.UserController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("users")
public class MealticketController {
  
  private static final Logger logger = LoggerFactory.getLogger(MealticketController.class);
  
  @Autowired
  private MealticketService mealticketService;
  
  @ApiOperation(value = "식권 발급", notes = "n개의 식권을 발급받습니다.")
  @GetMapping("mealticket")
  public ResponseEntity<String> mealticket(@RequestParam Integer mealticketCount, Integer weddingSeq) {
    logger.info("티켓 발급"+mealticketCount + " "+weddingSeq);
    mealticketService.getticket(mealticketCount, weddingSeq);
    return ResponseEntity.ok("티켓발급 완료");
  }
  
  @ApiOperation(value = "식권 사용", notes = "식권을 만료처리합니다.")
  @PutMapping("expriemealticket")
  public ResponseEntity<String> expriemealticket(@RequestBody MealTicket request) throws Exception {
    logger.info("식권 사용여부 확인");
    mealticketService.expireticket(request);
    return ResponseEntity.ok("식권 만료");
  }
  
}
