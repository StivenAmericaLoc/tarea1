package com.api.rest.americanloc.back.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.api.rest.americanloc.back.user.entity.UserToken;

public interface IUserTokenService {
	
	public ResponseEntity<List<UserToken>> findAll();
	
	public ResponseEntity<String> getTokenUser(String login, String pass);
	
	public boolean isAvalibleToken(String token);
	
}
