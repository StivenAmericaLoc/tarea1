package com.api.rest.americanloc.back.config.dao;

import org.springframework.data.repository.CrudRepository;

import com.api.rest.americanloc.back.config.entity.Config;

public interface IConfigDAO extends CrudRepository<Config, Integer>{

}
