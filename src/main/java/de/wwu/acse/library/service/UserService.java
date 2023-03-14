package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.service.exception.UsernameAlreadyExists;

/**
 * Interface for user management.
 * 
 * @author Henning Heitkoetter
 *
 */
public interface UserService {
	User createUser(String name, String address, String plaintextPassword) throws UsernameAlreadyExists;

	User createUser(User newUser) throws UsernameAlreadyExists;

	Collection<User> getAllUsers();

	User getUser(int userId) throws NoSuchElementException;

	void login(User user);
}
