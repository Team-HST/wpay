package com.hst.wpay.remittance.repository;

import com.hst.wpay.remittance.model.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
	List<Remittance> findByGuest_Sequence(Long gustSequence);
}
