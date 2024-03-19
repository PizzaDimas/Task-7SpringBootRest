package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
	private final UserService userService;


	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("/user")
	public String readById(Model model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = (User) userService.loadUserByUsername(userDetails.getUsername());
		model.addAttribute("user", user);
		return "user";
	}
	
}