package de.wwu.acse.library.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.wwu.acse.library.data.model.Copy;

public interface CopyRepository extends JpaRepository<Copy, Integer> {

	@Query("SELECT c FROM Copy c WHERE c.medium.id = :mediumId")
	List<Copy> findByMediumId(int mediumId);

	// This will automatically check for the ID and whether the loan is null
	List<Copy> findByMediumIdAndLoanIsNull(int mediumId);

}
