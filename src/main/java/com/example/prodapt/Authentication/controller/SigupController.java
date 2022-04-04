package com.example.prodapt.Authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.prodapt.Authentication.entity.UserPostingDetails;
import com.example.prodapt.Authentication.repository.UserDetailsRepository;

@RestController
@CrossOrigin
public class SigupController {

	@Autowired
	private UserDetailsRepository userDetailsRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@PostMapping("/submit")
	public ResponseEntity<Object> saveuserDetails(@RequestBody UserPostingDetails user) {
		try {
			UserPostingDetails u = new UserPostingDetails();
			UserPostingDetails userpos = userDetailsRepo.findByEmail(user.getEmail());
			if (userpos == null) {
				u.setFirstName(user.getFirstName());
				u.setLastName(user.getLastName());
				u.setEmail(user.getEmail());
				u.setLocation(user.getLocation());
				u.setExpLevel(user.getExpLevel());
				u.setMobileNo(user.getMobileNo());
				u.setPassword(encoder.encode(user.getPassword()));
				u.setProfileLink(user.getProfileLink());
			} else {
				Boolean passwordchecker = encoder.matches(user.getPassword(), userpos.getPassword());
				userpos.setFirstName(user.getFirstName());
				userpos.setLastName(user.getLastName());
				userpos.setEmail(user.getEmail());
				userpos.setLocation(user.getLocation());
				userpos.setMobileNo(user.getMobileNo());
				userpos.setPassword(passwordchecker ? userpos.getPassword() : encoder.encode(user.getPassword()));
				userpos.setExpLevel(user.getExpLevel());
				userpos.setProfileLink(user.getProfileLink());
			}
			return ResponseEntity.ok(userDetailsRepo.save(userpos == null ? u : userpos));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Email ID already exists,Please enter a unique email");

		}

	}
}
