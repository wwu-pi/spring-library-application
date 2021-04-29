package de.wwu.acse.library.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.data.repo.UserRepository;
import de.wwu.acse.library.service.exception.UsernameAlreadyExists;

@Service
public class StdUserService implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(String name, String address, String plaintextPassword) throws UsernameAlreadyExists {
		User newUser = new User();
		newUser.setUsername(name);
		newUser.setAddress(address);
		newUser.setPassword(passwordEncoder.encode(plaintextPassword));
		return createUser(newUser);
	}

	@Override
	public User createUser(User newUser) throws UsernameAlreadyExists {
		// Returns a proper representation of the user in the database.
		try {
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			return userRepository.save(newUser);
		} catch (DataIntegrityViolationException e) {
			throw new UsernameAlreadyExists();
		}
	}

	@Override
	public Collection<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(int userId) throws NoSuchElementException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new NoSuchElementException("The element could not be found.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User result = userRepository.findByUsername(username);
		if (result == null) {
			throw new UsernameNotFoundException(String.format("User with name %s cannot be found.", username));
		}
		return result;
	}

	@Override
	public void login(User user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
	}
}
