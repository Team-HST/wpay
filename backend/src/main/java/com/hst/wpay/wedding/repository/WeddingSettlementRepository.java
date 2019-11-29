package com.hst.wpay.wedding.repository;

import com.hst.wpay.wedding.model.entity.Wedding;
import com.hst.wpay.wedding.model.entity.WeddingSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface WeddingSettlementRepository extends JpaRepository<WeddingSettlement, Long> {
	Optional<WeddingSettlement> findByWedding(Wedding wedding);
}
