package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.converters.RoleConverter;
import com.runa.lib.api.dao.IRoleDao;
import com.runa.lib.api.dto.RoleDto;
import com.runa.lib.api.service.IRoleService;
import com.runa.lib.entities.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Override
	public List<RoleDto> getAllRoles() {
		return RoleConverter.convertList(roleDao.getAll());
	}

	@Override
	public RoleDto addRole(RoleDto roleDto) {
		Role role = new Role();
		role.setName(roleDto.getName());
		return RoleConverter.entityToDto(roleDao.create(role));
	}

	@Override
	public RoleDto getRoleById(Long id) {
		return Optional.ofNullable(RoleConverter.entityToDto(roleDao.get(id))).orElse(new RoleDto());
	}

	@Override
	public void deleteRoleById(Long id) {
		roleDao.delete(roleDao.get(id));
		log.info("Role successfully deleted");
	}

	@Override
	public void updateRole(Long id, RoleDto roleDto) {
		Role existingRole = Optional.ofNullable(roleDao.get(id)).orElse(new Role());
		existingRole.setName(roleDto.getName());
		roleDao.update(existingRole);
		log.info("Role successfully updated");

	}
}