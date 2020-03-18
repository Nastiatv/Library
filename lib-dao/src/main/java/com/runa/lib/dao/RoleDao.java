package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IRoleDao;
import com.runa.lib.entities.Role;

@Repository
public class RoleDao extends AGenericDao<Role> implements IRoleDao {

	public RoleDao() {
		super(Role.class);
	}
}