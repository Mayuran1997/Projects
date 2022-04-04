package com.example.prodapt.Authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prodapt.Authentication.entity.UserPostingDetails;

public interface UserDetailsRepository extends JpaRepository<UserPostingDetails, Long>{

	UserPostingDetails findByEmail(String email);
}
