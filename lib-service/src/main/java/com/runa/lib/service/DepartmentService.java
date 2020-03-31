package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.converters.DepartmentConverter;
import com.runa.lib.api.dao.IDepartmentDao;
import com.runa.lib.api.dto.DepartmentDto;
import com.runa.lib.api.service.IDepartmentService;
import com.runa.lib.entities.Department;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentDao departmentDao;

	@Override
	public List<DepartmentDto> getAllDepartments() {
		return DepartmentConverter.convertList(departmentDao.getAll());
	}

	@Override
	public DepartmentDto addDepartment(DepartmentDto departmentDto) {
		Department department = new Department();
		department.setName(departmentDto.getName());
		return DepartmentConverter.entityToDto(departmentDao.create(department));
	}

	@Override
	public DepartmentDto getDepartmentById(Long id) {
		return Optional.ofNullable(DepartmentConverter.entityToDto(departmentDao.get(id))).orElse(new DepartmentDto());
	}

	@Override
	public void deleteDepartmentById(Long id) {
		departmentDao.delete(departmentDao.get(id));
		log.info("Department successfully deleted");
	}

	@Override
	public void updateDepartment(Long id, DepartmentDto departmentDto) {
		Department existingDepartment = Optional.ofNullable(departmentDao.get(id)).orElse(new Department());
		existingDepartment.setName(departmentDto.getName());
		departmentDao.update(existingDepartment);
		log.info("Department successfully updated");

	}
}