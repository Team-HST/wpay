package com.hst.wpay.wedding.repository;

import com.hst.wpay.wedding.model.entity.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeddingRepository extends JpaRepository<Wedding, Long>{
	@Query("select W.sequence from Wedding W " +
			"where W.maleHost = ?1 and W.femaleHost = ?1")
	Long findByHostSeq(Long hostSequence);
}
