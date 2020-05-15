package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.entities.Department;
import by.runa.lib.entities.Role;
import by.runa.lib.entities.User;
import by.runa.lib.utils.mappers.AMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AMapper<User, UserDto> userMapper;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IDepartmentDao departmentDao;

    public IAGenericDao<User> getUserDao() {
        return userDao;
    }

    public IAGenericDao<Role> getRoleDao() {
        return roleDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toListDto(getUserDao().getAll());
    }

    @Override
    public UserDto createUser(UserDto userDto, DepartmentDto departmentDto) {
        return userMapper.toDto(getUserDao().create(writeUser(userDto, departmentDto)));
    }

    @Override
    public UserDto getUserById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(userMapper.toDto(getUserDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public UserDto getUserByEmail(String email) throws EntityNotFoundException {
        return Optional.ofNullable(userMapper.toDto(userDao.getByEmail(email)))
                .orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public UserDto getUserByName(String name) throws EntityNotFoundException {
        return Optional.ofNullable(userMapper.toDto(userDao.getByName(name)))
                .orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public void deleteUserById(Long id) {
        getUserDao().delete(getUserDao().get(id));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws EntityNotFoundException {
        User existingUser = Optional.ofNullable(getUserDao().get(id))
                .orElseThrow(() -> new EntityNotFoundException("User"));
        Optional.ofNullable(userDto.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(userDto.getUsername()).ifPresent(existingUser::setUsername);
        if (StringUtils.isBlank(userDto.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (StringUtils.isBlank(departmentDto.getName())) {
            existingUser.setDepartment(getDepartmentByName(departmentDto));
        }
        getUserDao().update(existingUser);
        return userMapper.toDto(existingUser);
    }

    private User writeUser(UserDto userDto, DepartmentDto departmentDto) {
        return new User().setEmail(userDto.getEmail()).setUsername(userDto.getUsername())
                .setDepartment(getDepartmentByName(departmentDto))
                .setPassword(passwordEncoder.encode(userDto.getPassword()))
                .setRoles(Collections.singletonList(getRoleDao().get(2L)));
    }

    private Department getDepartmentByName(DepartmentDto departmentDto) {
        return departmentDao.getByName(departmentDto.getName());
    }

}