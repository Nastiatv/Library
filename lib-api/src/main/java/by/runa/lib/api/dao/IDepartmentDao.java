package by.runa.lib.api.dao;

import by.runa.lib.entities.Department;

public interface IDepartmentDao extends IAGenericDao<Department> {

	Department getByName(String name);
}
