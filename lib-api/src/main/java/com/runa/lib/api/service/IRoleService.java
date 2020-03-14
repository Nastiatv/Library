package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.RoleDto;

public interface IRoleService {

	List<RoleDto> getAllRoles();

	RoleDto addRole(RoleDto dto);

	RoleDto getRoleById(Long id);

	void deleteRoleById(Long id);

	void updateRole(Long id, RoleDto dto);

}
