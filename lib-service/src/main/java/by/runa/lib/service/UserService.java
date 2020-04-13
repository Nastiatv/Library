package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.entities.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserService implements IUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AMapper<User, UserDto> userMapper;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IDepartmentDao departmentDao;

	@Override
	public List<UserDto> getAllUsers() {
		return userMapper.toListEntities(userDao.getAll());
	}

	@Override
	public UserDto addUser(UserDto userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setDepartment(departmentDao.getByName(userDto.getDepartmentName()));
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return userMapper.toDto(userDao.create(user));
	}

	@Override
	public UserDto getUserById(Long id) throws Exception {
		return Optional.ofNullable(userMapper.toDto(userDao.get(id))).orElseThrow(Exception::new);
	}
	
	@Override
	public UserDto getUserByEmail(String email) throws Exception {
		return Optional.ofNullable(userMapper.toDto(userDao.getByEmail(email))).orElseThrow(Exception::new);
	}

	@Override
	public void deleteUserById(Long id) {
		userDao.delete(userDao.get(id));
		log.info("User successfully deleted");
	}

	@Override
	public UserDto updateUser(Long id, UserDto userDto) {
		User existingUser = Optional.ofNullable(userDao.get(id)).orElse(new User());
		existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		existingUser.setEmail(userDto.getEmail());
		existingUser.setName(userDto.getName());
		userDao.update(existingUser);
		return userMapper.toDto(existingUser);

	}
}