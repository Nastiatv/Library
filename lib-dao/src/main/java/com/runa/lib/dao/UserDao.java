package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.User;

@Repository
public class UserDao extends AGenericDao<User> {

	public UserDao(Class<User> clazz) {
		super(User.class);
	}
}