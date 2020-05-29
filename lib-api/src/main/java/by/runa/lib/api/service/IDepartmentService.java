package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;

public interface IDepartmentService {

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long id) throws EntityNotFoundException;

    void deleteDepartmentById(Long id) throws EntityNotFoundException;

    DepartmentDto updateDepartment(Long id, DepartmentDto dto) throws EntityNotFoundException;

    DepartmentDto createDepartment(DepartmentDto departmentDto);

}
