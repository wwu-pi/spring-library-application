package de.wwu.acse.library.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import de.wwu.acse.library.data.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
