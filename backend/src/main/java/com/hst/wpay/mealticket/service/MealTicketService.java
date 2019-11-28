package com.hst.wpay.mealticket.service;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;
import com.hst.wpay.mealticket.model.entity.MealTicket;
import com.hst.wpay.mealticket.repository.MealticketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class MealTicketService {

  private static final Logger logger = LoggerFactory.getLogger(MealTicketService.class);

  private final MealticketRepository mealticketRepository;
  
  @Autowired
  public MealTicketService(MealticketRepository mealticketRepository) {
    this.mealticketRepository = mealticketRepository;
  }

  public void issueMealTicket(Integer mealTicketCount, Integer weddingSeq) {
    MealTicket mealticket = new MealTicket();
    mealticket.setMealCount(mealTicketCount);
    mealticket.setUseCheck("N");
    mealticket.setDateTime(LocalDateTime.now());
    mealticket.setWeddingSeq(weddingSeq);
    
    mealticketRepository.save(mealticket);
  }
  
  public void useMealTicket(MealTicket request) {
    MealTicket mealTicket = mealticketRepository.findBySequence(request.getSequence())
            .orElseThrow(() ->  new ReportableException(ResponseDescription.MEALTICKET_EXPIRATION, String.format(
            "존재하지 않는 식권입니다. 식권 SEQ: %d", request.getSequence())));

    if (mealTicket.isExpired()) {
      throw new ReportableException(ResponseDescription.MEALTICKET_EXPIRATION, String.format(
              "기간이 만료된 식권입니다. 식권 SEQ: %d", request.getSequence()));
    }

    MealTicket mealticket = new MealTicket();
    mealticket.setSequence(request.getSequence());
    mealticket.setDateTime(request.getDateTime());
    mealticket.setMealCount(request.getMealCount());
    mealticket.setUseCheck("Y");
    mealticket.setWeddingSeq(request.getWeddingSeq());
    
    mealticketRepository.save(mealticket);
  }

  /***
   * 결혼식에 발급된 총 식권 갯수 조회
   * @param weddingSequence 결혼 SEQ
   * @return 발급된 식권 갯수
   */
  public int getTotalIssuedMealTicketCount(Long weddingSequence) {
    int totalMealTicketCount = mealticketRepository.findTotalMealTicketCount(weddingSequence);
    if (totalMealTicketCount == 0) {
      logger.warn("해당 결혼에 발급된 식권이 없습니다. weddingSequence: {}", weddingSequence);
    }
    return totalMealTicketCount;
  }

}
