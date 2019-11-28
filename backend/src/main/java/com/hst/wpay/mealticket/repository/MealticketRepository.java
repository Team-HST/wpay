package com.hst.wpay.mealticket.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.hst.wpay.mealticket.model.entity.MealTicket;
import org.springframework.data.jpa.repository.Query;

public interface MealticketRepository extends JpaRepository<MealTicket, Long>{

  Optional<MealTicket> findBySequence(Long sequence);

  @Query("select sum(m.mealCount) from MealTicket m where m.weddingSeq = ?1")
  Integer findTotalMealTicketCount(Long weddingSequence);

}
