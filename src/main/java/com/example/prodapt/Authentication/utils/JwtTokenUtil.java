package com.example.prodapt.Authentication.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.prodapt.Authentication.entity.UserPostingDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY =  5 * 60 * 60;
	

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiry.minutes}")
	private Integer expiryTime;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserPostingDetails user) { 
		Map<String, Object> claims = new HashMap<>();
		claims.put("applicantId", user.getApplicantId());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		claims.put("email", user.getEmail());
		claims.put("mobileNo", user.getMobileNo());
		return doGenerateToken(claims, user);
	}

	
	
	public String doGenerateToken(Map<String, Object> claims,UserPostingDetails user ) { 
		 
		
		return Jwts.builder().setClaims(claims).setSubject(user.getEmail()).setIssuedAt(Date.from(Instant.now()))
		.setExpiration(Date.from(Instant.now().plus(expiryTime, ChronoUnit.MINUTES)))
		.signWith(SignatureAlgorithm.HS256, secret).compact();
		}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
