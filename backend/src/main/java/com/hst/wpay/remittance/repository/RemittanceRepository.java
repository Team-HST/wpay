package com.hst.wpay.remittance.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hst.wpay.remittance.model.entity.Remittance;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
	@Query("select R from Remittance R " + 
			"where R.guest.sequence = ?1 and function('date_format', R.remittanceAt, '%Y%m') = ?2")
	List<Remittance> findByGuest_Sequence(Long guestSequence, String searchMonth);

	@Query("select R from Remittance R " + 
			"where R.weddingSequence = ?1 and R.host.sequence = ?2")
	List<Remittance> findByWeddingSequence(Long weddingSequence, Long hostSequence);

	@Query("select sum(R.amount) from Remittance R " +
			"where R.weddingSequence = ?1")
	Long findTotalAmountByWeddingSequence(Long weddingSequence);

	@Query("select sum(R.amount) from Remittance R " +
			"where R.host.sequence = ?1 and R.weddingSequence = ?2")
	Long findTotalAmountByHostSequence(Long hostSequence, Long weddingSequence);
}
