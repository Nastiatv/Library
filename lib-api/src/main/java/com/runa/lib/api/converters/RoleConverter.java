package com.runa.lib.api.converters;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.api.dto.RoleDto;
import com.runa.lib.entities.Role;

public class RoleConverter {

	public static List<RoleDto> convertList(List<Role> entities) {
		List<RoleDto> listDto = new ArrayList<>();
		for (Role Role : entities) {
			RoleDto dto = new RoleDto();
			dto.setId(Role.getId());
			dto.setName(Role.getName());
			listDto.add(dto);
		}
		return listDto;
	}

	public static RoleDto entityToDto(Role entity) {
		RoleDto dto = new RoleDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setName(entity.getName());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Role dtoToEntity(RoleDto dto) {
		Role entity = new Role();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}
}
