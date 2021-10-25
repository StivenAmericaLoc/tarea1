package com.api.rest.americanloc.back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.americanloc.back.user.entity.User;
import com.api.rest.americanloc.back.user.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/get")
	public ResponseEntity<User> getUserByLoginAndPass(@RequestParam String login, @RequestParam String pass) {
		return userService.findByLoginAndPass(login, pass);
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> findAll(){
		return userService.findAll();
	}
}
