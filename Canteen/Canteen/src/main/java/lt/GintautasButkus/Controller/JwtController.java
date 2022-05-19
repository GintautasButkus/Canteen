package lt.GintautasButkus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.GintautasButkus.Entity.JwtRequest;
import lt.GintautasButkus.Entity.JwtResponse;
import lt.GintautasButkus.Service.JwtService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class JwtController {
	
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/authenticate")
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) {
		return jwtService.createJwtToken(jwtRequest);
		
	}
}
