package ru.itmentor.spring.boot_security.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/api/")
public class RestUserController {
	private final UserService userService;


	@Autowired
	public RestUserController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/user")
	@ResponseBody
	public User readById( Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return (User) userService.loadUserByUsername(userDetails.getUsername());
	}
	
}