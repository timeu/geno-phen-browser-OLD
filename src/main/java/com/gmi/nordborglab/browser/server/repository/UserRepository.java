package com.gmi.nordborglab.browser.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmi.nordborglab.browser.server.domain.acl.AppUser;


public interface UserRepository extends JpaRepository<AppUser, String> {
	
	public AppUser findByEmail(String email);
}
