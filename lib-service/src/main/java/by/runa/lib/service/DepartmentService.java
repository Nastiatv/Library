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
import by.runa.lib.exceptions.NoDepartmentWithThisIdException;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentDao departmentDao;

	@Autowired
	private AMapper<Department, DepartmentDto> departmentMapper;

	@Override
	public List<DepartmentDto> getAllDepartments() {
		return departmentMapper.toListDto(departmentDao.getAll());
	}

	@Override
	public DepartmentDto createDepartment(DepartmentDto departmentDto) {
		Department department = new Department();
		department.setName(departmentDto.getName());
		return departmentMapper.toDto(departmentDao.create(department));
	}

	@Override
	public DepartmentDto getDepartmentById(Long id) throws NoDepartmentWithThisIdException {
		return Optional.ofNullable(departmentMapper.toDto(departmentDao.get(id)))
				.orElseThrow(NoDepartmentWithThisIdException::new);
	}

	@Override
	public void deleteDepartmentById(Long id) {
		departmentDao.delete(departmentDao.get(id));
	}

	@Override
	public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) throws NoDepartmentWithThisIdException {
		Department existingDepartment = Optional.ofNullable(departmentDao.get(id))
				.orElseThrow(NoDepartmentWithThisIdException::new);
		existingDepartment.setName(departmentDto.getName());
		departmentDao.update(existingDepartment);
		return departmentMapper.toDto(existingDepartment);

	}
}