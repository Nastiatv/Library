package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;

public interface IDepartmentService {

	List<DepartmentDto> getAllDepartments();

	DepartmentDto getDepartmentById(Long id);

	void deleteDepartmentById(Long id);

	DepartmentDto updateDepartment(Long id, DepartmentDto dto);

	DepartmentDto createDepartment(DepartmentDto departmentDto);

}
