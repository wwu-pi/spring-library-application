package de.wwu.acse.library.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import de.wwu.acse.library.data.model.Medium;

public interface MediumRepository<T extends Medium> extends JpaRepository<T, Integer> {
	// If we had any shared logic for different Medium-subclasses, we can collect it here

}
