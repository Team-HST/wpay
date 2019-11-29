package com.hst.wpay.wedding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hst.wpay.wedding.model.entity.Wedding;

public interface WeddingRepository extends JpaRepository<Wedding, Long>{
	@Query("select W.sequence from Wedding W " +
			"where W.maleHost.sequence = ?1 or W.femaleHost.sequence = ?1")
	Long findByHostSeq(Long seq);
}
