package by.runa.lib.utils.mappers;

import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.entities.Role;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AMapper<Role, RoleDto> {

    public RoleMapper() {
        super(Role.class, RoleDto.class);
    }

}
