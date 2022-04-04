package com.example.prodapt.Authentication.entity;

import java.io.Serializable;

public class AuthResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	
	
	//private UserPostingDetails userdetails;
	public AuthResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	

	/*
	 * public AuthResponse(String token, UserPostingDetails user) { this.jwttoken =
	 * token; this.userdetails=user; // TODO Auto-generated constructor stub }
	 */

	public String getToken() {
	return this.jwttoken;
	}


	
	/*
	 * public UserPostingDetails getUserdetails() { return userdetails; }
	 */

	

	/*
	 * public User getUser() { return user; }
	 */
	
	
	
}
