package com.example.prodapt.Authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.prodapt.Authentication.entity.AuthRequest;
import com.example.prodapt.Authentication.entity.AuthResponse;
import com.example.prodapt.Authentication.entity.UserPostingDetails;
import com.example.prodapt.Authentication.repository.UserDetailsRepository;
import com.example.prodapt.Authentication.utils.JwtTokenUtil;

@RestController
@CrossOrigin
public class AuthenticationContoller {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private BCryptPasswordEncoder passwordencoder;

	@Autowired
	private UserDetailsRepository userRepo;

	@GetMapping("/")
	public String Hello() {
		return "Welcome";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authrequest) throws Exception {

		String token = null;
		UserPostingDetails user = userRepo.findByEmail(authrequest.getEmail());
		try {

			if (user != null) {
				Boolean isMatched = passwordencoder.matches(authrequest.getPassword(), user.getPassword());
				if (isMatched)
					token = jwtTokenUtil.generateToken(user); 
				else
					return (ResponseEntity<?>) ResponseEntity.badRequest();

			} else
				return (ResponseEntity<?>) ResponseEntity.badRequest();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid Username or Password");
		}

		return ResponseEntity.ok(new AuthResponse(token));
	}
	
	@GetMapping("/onboarding")
	public String getOnboardPage(){
		return "Success";
	}
}
