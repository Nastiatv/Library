package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.DepartmentDto;

public interface IDepartmentService {

	List<DepartmentDto> getAllDepartments();

	DepartmentDto addDepartment(DepartmentDto dto);

	DepartmentDto getDepartmentById(Long id);

	void deleteDepartmentById(Long id);

	void updateDepartment(Long id, DepartmentDto dto);

}
