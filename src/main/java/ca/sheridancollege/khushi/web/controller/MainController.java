package ca.sheridancollege.khushi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

}
