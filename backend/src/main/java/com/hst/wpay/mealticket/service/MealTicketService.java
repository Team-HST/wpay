package com.hst.wpay.mealticket.service;

import com.google.common.base.Optional;
import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;
import com.hst.wpay.mealticket.model.entity.MealTicket;
import com.hst.wpay.mealticket.model.request.MealTicketIssueRequest;
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

  public MealTicket issueMealTicket(MealTicketIssueRequest request) {
    MealTicket mealticket = new MealTicket();
    mealticket.setMealCount(request.getMealTicketCount());
    mealticket.setUseCheck("N");
    mealticket.setDateTime(LocalDateTime.now());
    mealticket.setWeddingSeq(request.getWeddingSeq());
    
    return mealticketRepository.save(mealticket);
  }
  
  public void useMealTicket(Long mealTicketSequence) {
    MealTicket mealTicket = mealticketRepository.findById(mealTicketSequence)
            .orElseThrow(() ->  new ReportableException(ResponseDescription.MEALTICKET_EXPIRATION, String.format(
            "존재하지 않는 식권입니다. 식권 SEQ: %d", mealTicketSequence)));

    if (mealTicket.isExpired()) {
      throw new ReportableException(ResponseDescription.MEALTICKET_EXPIRATION, String.format(
              "기간이 만료된 식권입니다. 식권 SEQ: %d", mealTicketSequence));
    }

    mealTicket.setUseCheck("Y");
    mealticketRepository.save(mealTicket);
  }

  /***
   * 결혼식에 발급된 총 식권 갯수 조회
   * @param weddingSequence 결혼 SEQ
   * @return 발급된 식권 갯수
   */
  public int getTotalIssuedMealTicketCount(Long weddingSequence) {
    int totalMealTicketCount = Optional.fromNullable(mealticketRepository.findTotalMealTicketCount(weddingSequence)).or(0);
    if (totalMealTicketCount == 0) {
      logger.warn("해당 결혼에 발급된 식권이 없습니다. weddingSequence: {}", weddingSequence);
    }
    return totalMealTicketCount;
  }


}
