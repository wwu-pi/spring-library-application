package de.wwu.acse.library.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
        // Allow public requests related to registering and creating a user
        	.requestMatchers(HttpMethod.GET, "/js/**", "/css/**", "/users/register")
        		.permitAll()
	        .requestMatchers(HttpMethod.POST, "/users/create")
	        	.permitAll()
	        // Allow to access the H2 console (only for debugging)
	        .requestMatchers(toH2Console())
			        	.permitAll()
	        // Disallow any other request, except if authenticated
	        .anyRequest()
	        	.authenticated()
	        	.and()
		     // Configure login
	        .formLogin()
	        	.loginPage("/users/login")
	        	.loginProcessingUrl("/users/performlogin")
	        // Send to profile after logging in
	        	.defaultSuccessUrl("/")
	        	.failureUrl("/users/login?error")
	        	.permitAll()
	        	.and()
		    // Configure logout
	        .logout()
	        	.logoutUrl("/users/logout") // This triggers a logout.
	        	.deleteCookies("JSESSIONID") // Delete cookies upon logout
	        	.logoutSuccessUrl("/users/login?logout") // Direct to login page with the logout-parameter set (will trigger the success-alert)
	        	.permitAll()
	        	.and()
		    // Disable CSRF only for H2 console (only for debugging)
	        .csrf()
	       		.ignoringRequestMatchers(toH2Console())
		    // Set X-Frame-Options to sameorigin to allow the H2 console to function (only for debugging)
	       		.and().headers().frameOptions().sameOrigin();

		return http.build();
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