package com.example.prodapt.Authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.prodapt.Authentication.filters.JwtFilter;
import com.example.prodapt.Authentication.service.CustomUserDetails;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetails userDetails;
	
	@Autowired private JwtFilter filter;

	@Override // for authentication we need to configure this in spring security
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetails);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override // for authorization we need to configure this in spring security
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable().cors().and().authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.antMatchers("/submit").permitAll()
		.anyRequest().authenticated().and()
		  .exceptionHandling().and().sessionManagement()
		  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		  // Add a filter to validate the tokens with every request
		  http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
	}

}
