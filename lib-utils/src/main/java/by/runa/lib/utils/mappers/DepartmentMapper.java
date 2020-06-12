package by.runa.lib.utils.mappers;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.entities.Department;

import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper extends AMapper<Department, DepartmentDto> {

    public DepartmentMapper() {
        super(Department.class, DepartmentDto.class);
    }
}