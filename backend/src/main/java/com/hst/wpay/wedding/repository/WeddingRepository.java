package com.hst.wpay.wedding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hst.wpay.wedding.model.entity.Wedding;

public interface WeddingRepository extends JpaRepository<Wedding, Long>{

}
