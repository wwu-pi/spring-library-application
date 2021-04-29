package de.wwu.acse.library.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.wwu.acse.library.data.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

	// In Spring SQL queries we can treat, e.g., l like an object.
	// There is no explicit need to perform a JOIN here.
	@Query("SELECT l FROM Loan l WHERE l.user.uid = :userId")
	List<Loan> findByUserId(@Param("userId") int userId);

}
