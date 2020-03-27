package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.entities.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

	private Long id;
	private String name;

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
}
