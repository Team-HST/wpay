package com.hst.wpay.mealticket.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.hst.wpay.mealticket.model.entity.MealTicket;

public interface MealticketRepository extends JpaRepository<MealTicket, Long>{

  Optional<MealTicket> findBySequence(long sequence);

}
