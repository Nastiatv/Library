package by.runa.lib.api.mappers;

import org.springframework.stereotype.Component;

import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.entities.Role;

@Component
public class RoleMapper extends AMapper<Role, RoleDto> {

	public RoleMapper() {
		super(Role.class, RoleDto.class);
	}

}
