package com.hst.wpay.mealticket.model.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name="MEAL_TICKET")
@Data
public class MealTicket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="SEQ")
  private long sequence;
  
  @Column(name="MEAL_CNT")
  private Integer mealCount;
  
  @Column(name="USE_YN")
  private String useCheck;
  
  
  @Column(name="REG_DT")
  @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime dateTime;
  
  @Column(name="WEDDING_SEQ")
  private long weddingSeq;

  public boolean isExpired() {
    return "Y".equals(this.useCheck) || this.dateTime.isAfter(LocalDateTime.now().plusDays(1));
  }
}
