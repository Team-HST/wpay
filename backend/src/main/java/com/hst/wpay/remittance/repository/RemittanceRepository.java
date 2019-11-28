package com.hst.wpay.remittance.repository;

import com.hst.wpay.remittance.model.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

	List<Remittance> findByGuest_Sequence(Long guestSequence);

	List<Remittance> findByWeddingSequence(Long weddingSequence);

	@Query("select sum(R.amount) from Remittance R " +
			"where R.weddingSequence = ?1")
	long findTotalAmountByWeddingSequence(Long weddingSequence);

	@Query("select sum(R.amount) from Remittance R " +
			"where R.host.sequence = ?1")
	long findTotalAmountByHostSequence(Long hostSequence);
}
