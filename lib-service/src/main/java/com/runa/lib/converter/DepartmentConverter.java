package com.runa.lib.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.runa.lib.api.dao.IDepartmentDao;
import com.runa.lib.entities.Department;

@Component
public class DepartmentConverter {

	@Autowired
	private IDepartmentDao departmentDao;

	public String convertToDatabaseColumn(List<Department> departments) {
		StringBuilder departmentsInString = new StringBuilder();
		for (Department department : departments) {
			departmentsInString = departmentsInString.append(department + ", ");
		}
		return departmentsInString.toString();
	}

	public List<Department> convertToEntityAttribute(String departmentsInString) {
		List<Department> departments = new ArrayList<>();
		String[] list = departmentsInString.split(", ");
		for (String dep : list) {
			departments.add(departmentDao.getAll().stream().filter(c -> c.getName().equals(dep)).findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Invalid enum number : " + dep)));
		}
		return departments;
	}
}
