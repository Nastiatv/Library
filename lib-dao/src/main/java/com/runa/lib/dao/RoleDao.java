package com.runa.lib.dao;

import com.runa.lib.entities.Role;

public class RoleDao extends AGenericDao<Role> {

	public RoleDao(Class<Role> clazz) {
		super(Role.class);
	}

}
