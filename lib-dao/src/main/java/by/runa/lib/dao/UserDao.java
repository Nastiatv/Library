package by.runa.lib.dao;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.entities.User;

@Repository
public class UserDao extends AGenericDao<User> implements IUserDao {

	public UserDao() {
		super(User.class);
	}
}