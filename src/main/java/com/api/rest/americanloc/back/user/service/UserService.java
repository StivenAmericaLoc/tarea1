package com.api.rest.americanloc.back.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.rest.americanloc.back.user.dao.IUserDAO;
import com.api.rest.americanloc.back.user.entity.User;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserDAO userDao;

	@Override
	public ResponseEntity<List<User>> findAll() {
		try {
			List<User> listUser = (List<User>) userDao.findAll();
			return new ResponseEntity<List<User>>(listUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<User> findByLoginAndPass(String login, String pass) {
		try {
			User user = userDao.findByLoginAndPass(login, pass);
			if (user != null) {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
