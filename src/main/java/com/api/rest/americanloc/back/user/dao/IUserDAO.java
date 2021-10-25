package com.api.rest.americanloc.back.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.api.rest.americanloc.back.user.entity.User;

public interface IUserDAO extends CrudRepository<User, Integer> {

	User findByLoginAndPass(String login, String pass);

}
