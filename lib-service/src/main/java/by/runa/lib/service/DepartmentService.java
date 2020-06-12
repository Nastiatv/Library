package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.exceptions.DepartmentIsNotEmptyException;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.entities.Department;
import by.runa.lib.utils.mappers.AMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {

    private static final String DEPARTMENT = "Department";

    @Autowired
    private IDepartmentDao departmentDao;

    @Autowired
    private IBookService bookService;

    @Autowired
    private AMapper<Department, DepartmentDto> departmentMapper;

    public IAGenericDao<Department> getDepartmentDao() {
        return departmentDao;
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentMapper.toListDto(getDepartmentDao().getAll());
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        return departmentMapper.toDto(getDepartmentDao().create(new Department().setName(departmentDto.getName())));
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(departmentMapper.toDto(getDepartmentDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException(DEPARTMENT));
    }

    @Override
    public DepartmentDto getDepartmentByName(String name) {
        return departmentMapper.toDto(departmentDao.getByName(name));
    }

    @Override
    public void deleteDepartmentById(Long id) throws EntityNotFoundException, DepartmentIsNotEmptyException {
        if (bookService.getBooksByDepartmentId(id) != null) {
            throw new DepartmentIsNotEmptyException();
        } else {
            getDepartmentDao().delete(getDepartmentDao().get(id));
        }
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) throws EntityNotFoundException {
        Department existingDepartment = Optional.ofNullable(getDepartmentDao().get(id))
                .orElseThrow(() -> new EntityNotFoundException(DEPARTMENT));
        Optional.ofNullable(departmentDto.getName()).ifPresent(existingDepartment::setName);
        getDepartmentDao().update(existingDepartment);
        return departmentMapper.toDto(existingDepartment);
    }
}