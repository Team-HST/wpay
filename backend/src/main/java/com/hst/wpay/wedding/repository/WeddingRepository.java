package com.hst.wpay.wedding.repository;

import com.hst.wpay.wedding.model.entity.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeddingRepository extends JpaRepository<Wedding, Long>{
}
