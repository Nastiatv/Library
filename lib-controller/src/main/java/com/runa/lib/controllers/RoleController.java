package com.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runa.lib.api.dto.RoleDto;
import com.runa.lib.api.service.IRoleService;

@RestController
@RequestMapping(value = "/roles/")
public class RoleController {

	private static final String ID = "{id}";

	@Autowired
	IRoleService roleService;

	@GetMapping
	public List<RoleDto> getAllRole() {
		return roleService.getAllRoles();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RoleDto addRole(@RequestBody RoleDto dto) {
		return roleService.addRole(dto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateRole(@PathVariable Long id, @RequestBody RoleDto dto) {
		roleService.updateRole(id, dto);
	}

	@GetMapping(value = ID)
	public RoleDto getRole(@PathVariable Long id) {
		return roleService.getRoleById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteRoleById(id);
	}
}
