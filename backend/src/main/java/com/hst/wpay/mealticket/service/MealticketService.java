package com.hst.wpay.mealticket.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hst.wpay.mealticket.model.entity.MealTicket;
import com.hst.wpay.mealticket.repository.MealticketRepository;


@Service
public class MealticketService {
  private static final Logger logger = LoggerFactory.getLogger(MealticketService.class);
  private final MealticketRepository mealticketRepository;
  
  @Autowired
  public MealticketService(MealticketRepository mealticketRepository) {
    this.mealticketRepository = mealticketRepository;
  }

  public void getticket(Integer mealticketCount, Integer weddingSeq) {
    MealTicket mealticket = new MealTicket();
    mealticket.setMealCount(mealticketCount);
    mealticket.setUseCheck("N");
    mealticket.setDateTime(LocalDateTime.now());
    mealticket.setWeddingSeq(weddingSeq);
    
    mealticketRepository.save(mealticket);
  }
  
  public void expireticket(MealTicket request) throws Exception {
    Optional<MealTicket> oPmealticket = mealticketRepository.findBySequence(request.getSequence());
    if(oPmealticket.get().getUseCheck().equals("Y")||checkTime(request.getDateTime())) {
      throw new Exception("만료된 식권입니다.");
    }
    MealTicket mealticket = new MealTicket();
    mealticket.setSequence(request.getSequence());
    mealticket.setDateTime(request.getDateTime());
    mealticket.setMealCount(request.getMealCount());
    mealticket.setUseCheck("Y");
    mealticket.setWeddingSeq(request.getWeddingSeq());
    
    mealticketRepository.save(mealticket);
  }

  public boolean checkTime(LocalDateTime ticketDate) {
    LocalDateTime expireTime = ticketDate.plusDays(1);    
    if(expireTime.isBefore(LocalDateTime.now())) {
      return true;
    }
    return false;
  }

}
