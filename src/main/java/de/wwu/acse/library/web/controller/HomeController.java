package de.wwu.acse.library.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wwu.acse.library.data.model.User;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String index(@AuthenticationPrincipal User currentUser) {
		if (currentUser == null) {
			return "/users/login";
		} else {
			return "redirect:/books";
		}
	}

}
