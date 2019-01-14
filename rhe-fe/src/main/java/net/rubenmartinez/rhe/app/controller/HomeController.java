package net.rubenmartinez.rhe.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.rubenmartinez.rhe.app.dao.domain.User;
import net.rubenmartinez.rhe.app.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Authentication authentication, Model model) {
		model.addAttribute("loggedUser", userService.getUserDTO((User) authentication.getPrincipal()));
		return "index";
	}
}
