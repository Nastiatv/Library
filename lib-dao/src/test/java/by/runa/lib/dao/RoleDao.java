package by.runa.lib.dao;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.entities.Role;

@Repository
public class RoleDao extends AGenericDao<Role> implements IRoleDao {

	public RoleDao() {
		super(Role.class);
	}
}