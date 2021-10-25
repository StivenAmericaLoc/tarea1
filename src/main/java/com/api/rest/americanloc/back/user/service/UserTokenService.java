package com.api.rest.americanloc.back.user.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.rest.americanloc.back.user.dao.IUserDAO;
import com.api.rest.americanloc.back.user.dao.IUserTokenDAO;
import com.api.rest.americanloc.back.user.entity.User;
import com.api.rest.americanloc.back.user.entity.UserToken;

@Service
public class UserTokenService implements IUserTokenService {
	
	@Autowired
	private IUserTokenDAO userTokenDao;
	
	@Autowired
	private IUserDAO userDao;

	@Override
	public ResponseEntity<List<UserToken>> findAll() {
		List<UserToken> listUser = null;
		try {
			listUser = (List<UserToken>) userTokenDao.findAll();
			return new ResponseEntity<List<UserToken>>(listUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserToken>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> getTokenUser(String login, String pass) {
		try {
			User user = userDao.findByLoginAndPass(login, pass);
			if (user != null) {
				String token = "";
				UserToken userToken = userTokenDao.findByUserId(user.getId());
				if (userToken == null) {
					userToken = new UserToken();
					Long contador = userTokenDao.count();
					userToken.setId(contador.intValue()+1);
					userToken.setUserId(user.getId());
					userToken.setToken(getToken(userToken.getUserId()));
					userToken.setMillisecondsDate(getMillisencodDate());
					userTokenDao.save(userToken);
					token = userToken.getToken();
				} else {
					Long miliseconds = userToken.getMillisecondsDate();
					if (isDateAvalible(miliseconds)) {
						token = userToken.getToken();
					} else {
						userToken.setToken(getToken(userToken.getUserId()));
						userToken.setMillisecondsDate(getMillisencodDate());
						userTokenDao.save(userToken);
						token = userToken.getToken();
					}
				}
				return new ResponseEntity<String>(token, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Usuario o contraseña invalido", HttpStatus.FORBIDDEN);
			}			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public boolean isAvalibleToken(String token) {
		try {
			UserToken userToken = userTokenDao.findByToken(token);
			if (userToken != null) {
				if(isDateAvalible(userToken.getMillisecondsDate())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
	}
	
	private String getToken(Integer userId) {
		return UUID.randomUUID().toString().toUpperCase() + userId;
	}
	
	private Long getMillisencodDate() {
		Calendar fecha = Calendar.getInstance();
		fecha.add(Calendar.HOUR, 5);
		return fecha.getTimeInMillis();
	}
	
	private boolean isDateAvalible(Long miliseconds) {
		Calendar fechaActual = Calendar.getInstance();
		Calendar fechaToken = Calendar.getInstance();
		fechaToken.setTimeInMillis(miliseconds);
		if (fechaActual.before(fechaToken)) {
			return true;
		} else {
			return false;
		}
	}
	

}
