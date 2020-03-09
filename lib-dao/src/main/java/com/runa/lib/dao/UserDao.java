package com.runa.lib.dao;

import com.runa.lib.entities.User;

public class UserDao extends AGenericDao<User>{

	public UserDao(Class<User> clazz) {
		super(User.class);
	}

}
