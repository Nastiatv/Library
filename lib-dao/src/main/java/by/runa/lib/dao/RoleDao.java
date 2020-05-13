package by.runa.lib.dao;

import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.entities.Role;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends AGenericDao<Role> implements IRoleDao {

	public RoleDao() {
		super(Role.class);
	}
}