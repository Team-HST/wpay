package com.hst.wpay.mealticket.service;

import com.hst.wpay.common.ReportableException;
import com.hst.wpay.common.type.ResponseDescription;
import com.hst.wpay.mealticket.model.entity.MealTicket;
import com.hst.wpay.mealticket.model.request.MealTicketIssueRequest;
import com.hst.wpay.mealticket.model.request.MealTicketUseRequest;
import com.hst.wpay.mealticket.repository.MealticketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class MealTicketService {

  private static final Logger logger = LoggerFactory.getLogger(MealTicketService.class);

  private final MealticketRepository mealticketRepository;
  
  @Autowired
  public MealTicketService(MealticketRepository mealticketRepository) {
    this.mealticketRepository = mealticketRepository;
  }

  public MealTicket getGuestActiveMealTicket(long guestSeq) {
    DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    Optional<MealTicket> mealTicket = mealticketRepository.findByGuestSequenceOrderByDateTimeDesc(guestSeq)
            .stream().filter(e -> LocalDateTime.now().format(yyyyMMdd).equals(e.getDateTime().format(yyyyMMdd)))
            .findFirst();
    return mealTicket.orElseThrow(() -> new ReportableException(ResponseDescription.MEALTICKET_NOT_ISSUED));
  }

  public MealTicket issueMealTicket(MealTicketIssueRequest request) {
    MealTicket mealTicket = new MealTicket();
    mealTicket.setMealCount(request.getMealTicketCount());
    mealTicket.setUseCheck("N");
    mealTicket.setDateTime(LocalDateTime.now());
    mealTicket.setGuestSequence(request.getGuestSeq());
    mealTicket.setWeddingSeq(request.getWeddingSeq());
    
    return mealticketRepository.save(mealTicket);
  }
  
  public void useMealTicket(MealTicketUseRequest request) {
    MealTicket mealTicket = mealticketRepository.findById(request.getMealTicketSequence())
            .orElseThrow(() ->  new ReportableException(ResponseDescription.MEALTICKET_EXPIRATION, String.format(
            "존재하지 않는 식권입니다. 식권 SEQ: %d", request.getMealTicketSequence())));

    if (mealTicket.getWeddingSeq() != request.getWeddingSequence()) {
      throw new ReportableException(ResponseDescription.MEALTICKET_INVALID_USE, String.format(
              "다른 결혼에서 발급된 식권입니다.. 식권 SEQ: %d, 결혼 SEQ: %d, 요청 결혼 SEQ: %d",
              request.getMealTicketSequence(), mealTicket.getWeddingSeq(), request.getWeddingSequence()));
    }

    if (mealTicket.isExpired()) {
      throw new ReportableException(ResponseDescription.MEALTICKET_EXPIRATION, String.format(
              "기간이 만료된 식권입니다. 식권 SEQ: %d", request.getMealTicketSequence()));
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
    int totalMealTicketCount = Optional.ofNullable(mealticketRepository.findTotalMealTicketCount(weddingSequence)).orElse(0);
    if (totalMealTicketCount == 0) {
      logger.warn("해당 결혼에 발급된 식권이 없습니다. weddingSequence: {}", weddingSequence);
    }
    return totalMealTicketCount;
  }

  public MealTicket getMealTicket(long seq) {
    return mealticketRepository.findById(seq)
            .orElseThrow(() -> new ReportableException(ResponseDescription.MEALTICKET_NOT_ISSUED));
  }
}
