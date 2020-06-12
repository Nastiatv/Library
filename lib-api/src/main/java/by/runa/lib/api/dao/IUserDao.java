package by.runa.lib.api.dao;

import java.util.List;

import by.runa.lib.entities.User;

public interface IUserDao extends IAGenericDao<User> {

    User getByEmail(String email);

    List<User> getByDepartment(String department);

    User getByName(String name);
}
