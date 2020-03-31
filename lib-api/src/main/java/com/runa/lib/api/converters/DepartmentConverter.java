package com.runa.lib.api.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.runa.lib.api.dao.IDepartmentDao;
import com.runa.lib.api.dto.DepartmentDto;
import com.runa.lib.entities.Department;

public class DepartmentConverter {

	@Autowired
	private IDepartmentDao departmentDao;

	public static List<DepartmentDto> convertList(List<Department> entities) {
		List<DepartmentDto> listDto = new ArrayList<>();
		for (Department Department : entities) {
			DepartmentDto dto = new DepartmentDto();
			dto.setId(Department.getId());
			dto.setName(Department.getName());
			listDto.add(dto);
		}
		return listDto;
	}

	public static DepartmentDto entityToDto(Department entity) {
		DepartmentDto dto = new DepartmentDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setName(entity.getName());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Department dtoToEntity(DepartmentDto dto) {
		Department entity = new Department();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

	public static String convertListDepartmentsToString(List<Department> departments) {
		String departmentsInString ="";
		for (Department department : departments) {
			departmentsInString = departmentsInString.concat(department + ", ");
		}
		return departmentsInString;
	}

	public List<Department> convertStringToListDepartments(String departmentsInString) {
		List<Department> departments = new ArrayList<>();
		String[] list = departmentsInString.split(", ");
		for (String dep : list) {
			departments.add(departmentDao.getAll().stream().filter(c -> c.getName().equals(dep)).findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Invalid enum number : " + dep)));
		}
		return departments;
	}
}
