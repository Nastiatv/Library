package by.runa.lib.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.entities.Department;
import by.runa.lib.entities.User;
import by.runa.lib.exceptions.NoUserWithThisIdException;

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

	@Override
	public List<UserDto> getAllUsers() {
		return userMapper.toListDto(userDao.getAll());
	}

	@Override
	public UserDto createUser(UserDto userDto, DepartmentDto departmentDto) {
		Department department = departmentDao.getByName(departmentDto.getName());
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setDepartment(department);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRoles(Collections.singletonList(roleDao.get(2L)));
		return userMapper.toDto(userDao.create(user));
	}

	@Override
	public UserDto getUserById(Long id) throws NoUserWithThisIdException {
		return Optional.ofNullable(userMapper.toDto(userDao.get(id))).orElseThrow(NoUserWithThisIdException::new);
	}

	@Override
	public UserDto getUserByEmail(String email) throws NoUserWithThisIdException {
		return Optional.ofNullable(userMapper.toDto(userDao.getByEmail(email)))
				.orElseThrow(NoUserWithThisIdException::new);
	}

	@Override
	public UserDto getUserByName(String name) throws NoUserWithThisIdException {
		return Optional.ofNullable(userMapper.toDto(userDao.getByName(name)))
				.orElseThrow(NoUserWithThisIdException::new);
	}

	@Override
	public void deleteUserById(Long id) {
		userDao.delete(userDao.get(id));
	}

	@Override
	public UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws NoUserWithThisIdException {
		User existingUser = Optional.ofNullable(userDao.get(id)).orElseThrow(NoUserWithThisIdException::new);
		if (StringUtils.isBlank(userDto.getPassword())) {
			existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		if (userDto.getEmail() != null) {
			existingUser.setEmail(userDto.getEmail());
		}
		if (userDto.getUsername() != null) {
			existingUser.setUsername(userDto.getUsername());
		}
		if (departmentDto.getName() != null) {
			existingUser.setDepartment(departmentDao.getByName(departmentDto.getName()));
		}
		userDao.update(existingUser);
		return userMapper.toDto(existingUser);
	}
}