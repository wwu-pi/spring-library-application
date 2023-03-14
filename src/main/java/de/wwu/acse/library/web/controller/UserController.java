package de.wwu.acse.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wwu.acse.library.data.model.User;
import de.wwu.acse.library.service.UserService;
import de.wwu.acse.library.service.exception.UsernameAlreadyExists;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "users/login";
	}

	@GetMapping("/register")
	public String register(Model m) {
		m.addAttribute("newUser", new User());
		return "users/register";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("newUser") @Valid User newUser, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "users/register";
		}
		try {
			userService.createUser(newUser);
		} catch (UsernameAlreadyExists e) {
			bindingResult.rejectValue("username", "error.user", "Username already exists");
			return "users/register";
		}
		// Automatically log in new user
		userService.login(newUser);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		return "redirect:/";
	}

}