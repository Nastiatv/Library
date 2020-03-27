package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IDepartmentDao;
import com.runa.lib.entities.Department;

@Repository
public class DepartmentDao extends AGenericDao<Department> implements IDepartmentDao {

	public DepartmentDao() {
		super(Department.class);
	}
}