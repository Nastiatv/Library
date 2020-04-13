package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.entities.Department;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentDao departmentDao;

	@Autowired
	private AMapper<Department, DepartmentDto> departmentMapper;

	@Override
	public List<DepartmentDto> getAllDepartments() {
		return departmentMapper.toListEntities(departmentDao.getAll());
	}

	@Override
	public DepartmentDto addDepartment(DepartmentDto departmentDto) {
		Department department = new Department();
		department.setName(departmentDto.getName());
		return departmentMapper.toDto(departmentDao.create(department));
	}

	@Override
	public DepartmentDto getDepartmentById(Long id) {
		return Optional.ofNullable(departmentMapper.toDto(departmentDao.get(id))).orElse(new DepartmentDto());
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