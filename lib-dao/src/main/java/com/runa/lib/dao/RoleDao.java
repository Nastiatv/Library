package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.Role;

@Repository
public class RoleDao extends AGenericDao<Role> {

	public RoleDao(Class<Role> clazz) {
		super(Role.class);
	}
}