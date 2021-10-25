package com.api.rest.americanloc.back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.americanloc.back.user.entity.UserToken;
import com.api.rest.americanloc.back.user.service.IUserTokenService;

@RestController
@RequestMapping("/auth")
public class UserTokenController {
	
	@Autowired
	private IUserTokenService userTokenService;
	
	@GetMapping("")
	public ResponseEntity<String> getToken(@RequestParam String login, @RequestParam String pass) {
		return userTokenService.getTokenUser(login, pass);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<UserToken>> findAll(){
		return userTokenService.findAll();
	}

}
