package de.wwu.acse.library.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// Allow public requests related to registering and creating a user
				.antMatchers(HttpMethod.GET, "/", "/css/*", "users/login", "/users/register").permitAll()
				.antMatchers(HttpMethod.POST, "/users/create", "/users/performlogin").permitAll()
				// Disallow any other request, except if authenticated
				.anyRequest().authenticated().and()
				// Configure login
				.formLogin().loginPage("/users/login").loginProcessingUrl("/users/performlogin")
				.defaultSuccessUrl("/books")
				.failureUrl("/users/login").permitAll().and()
				// Configure logout
				.logout().logoutUrl("/users/logout") // This triggers a logout.
				.deleteCookies("JSESSIONID") // Delete cookies upon logout
				.logoutSuccessUrl("/users/login") // Direct to login page with the logout-parameter set (will
															// trigger the success-alert)
				.permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider result = new DaoAuthenticationProvider();
		result.setUserDetailsService(userDetailsService);
		result.setPasswordEncoder(passwordEncoder());
		return result;
	}

}