package com.api.rest.americanloc.back.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.api.rest.americanloc.back.user.entity.User;

public interface IUserService {
	
	public ResponseEntity<List<User>> findAll();
	
	public ResponseEntity<User> findByLoginAndPass(String login, String pass);

}
