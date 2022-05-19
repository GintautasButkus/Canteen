package lt.GintautasButkus.Controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.GintautasButkus.Entity.Client;
import lt.GintautasButkus.Service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Component
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void init() {
		userService.initRolesAndUser();
	}
	
	@PostMapping("/registerNewUser")
	public Client registerNewUser(@RequestBody Client user) {
		return userService.registerNewUser(user);
	}
	
	
	@GetMapping("/forAdmin")
	@PreAuthorize("hasRole('admin')")
	public String forAdmin() {
		return "This URL is only accessible to admin.";
	}
	
	@GetMapping("/forUser")
	@PreAuthorize("hasAnyRole('user','admin')")
	public String forUser() {
		return "This URL is only accessible to user.";
	}

}
