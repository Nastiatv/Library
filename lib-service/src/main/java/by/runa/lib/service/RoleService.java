package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.api.service.IRoleService;
import by.runa.lib.entities.Role;
import by.runa.lib.utils.mappers.AMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private AMapper<Role, RoleDto> roleMapper;

	public IAGenericDao<Role> getRoleDao() {
		return roleDao;
	}

	@Override
	public List<RoleDto> getAllRoles() {
		return roleMapper.toListDto(getRoleDao().getAll());
	}

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		Role role = new Role();
		role.setName(roleDto.getName());
		return roleMapper.toDto(getRoleDao().create(role));
	}

	@Override
	public RoleDto getRoleById(Long id) {
		return Optional.ofNullable(roleMapper.toDto(getRoleDao().get(id))).orElse(new RoleDto());
	}

	@Override
	public void deleteRoleById(Long id) {
		getRoleDao().delete(getRoleDao().get(id));
		log.info("Role successfully deleted");
	}

	@Override
	public RoleDto updateRole(Long id, RoleDto roleDto) {
		Role existingRole = Optional.ofNullable(getRoleDao().get(id)).orElse(new Role());
		existingRole.setName(roleDto.getName());
		getRoleDao().update(existingRole);
		return roleMapper.toDto(existingRole);

	}
}