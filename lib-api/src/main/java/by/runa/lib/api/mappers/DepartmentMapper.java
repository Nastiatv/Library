package by.runa.lib.api.mappers;

import org.springframework.stereotype.Component;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.entities.Department;

@Component
public class DepartmentMapper extends AMapper<Department, DepartmentDto> {

	public DepartmentMapper() {
		super(Department.class, DepartmentDto.class);
	}

}
