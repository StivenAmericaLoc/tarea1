package com.api.rest.americanloc.back.user.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.api.rest.americanloc.back.user.entity.UserToken;

public interface IUserTokenDAO extends CrudRepository<UserToken, Integer> {
	
	@Query("select ut from UserToken ut where ut.userId = ?1")
	UserToken findByUserId(Integer userId);
	
	@Query("select ut from UserToken ut where ut.token = ?1")
	UserToken findByToken(String token);

}
