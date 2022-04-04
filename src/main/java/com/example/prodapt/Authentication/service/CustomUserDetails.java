package com.example.prodapt.Authentication.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.prodapt.Authentication.entity.UserPostingDetails;
import com.example.prodapt.Authentication.repository.UserDetailsRepository;

@Service
public class CustomUserDetails implements UserDetailsService {


	@Autowired
	private UserDetailsRepository userDetailRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPostingDetails user=userDetailRepo.findByEmail(username);
		
		return new User(user.getEmail(),user.getPassword(),new ArrayList<>()) ;
	}

}
