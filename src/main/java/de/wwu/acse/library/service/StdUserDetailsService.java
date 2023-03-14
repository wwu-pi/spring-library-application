package de.wwu.acse.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.data.repo.UserRepository;

@Service
//Separate implementation of UserDetailsService to avoid circular dependencies with StdUserService and PasswordEncoder in SecurityConfig
public class StdUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
}

