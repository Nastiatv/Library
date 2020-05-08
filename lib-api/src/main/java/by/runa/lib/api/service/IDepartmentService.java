package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.exceptions.NoDepartmentWithThisIdException;

public interface IDepartmentService {

	List<DepartmentDto> getAllDepartments();

	DepartmentDto getDepartmentById(Long id) throws NoDepartmentWithThisIdException;

	void deleteDepartmentById(Long id);

	DepartmentDto updateDepartment(Long id, DepartmentDto dto) throws NoDepartmentWithThisIdException;

	DepartmentDto createDepartment(DepartmentDto departmentDto);

}
