package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.runa.lib.api.converters.UserConverter;
import com.runa.lib.api.dao.IUserDao;
import com.runa.lib.api.dto.UserDto;
import com.runa.lib.api.service.IUserService;
import com.runa.lib.entities.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserService implements IUserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUserDao userDao;

	@Override
	public List<UserDto> getAllUsers() {
		return UserConverter.convertList(userDao.getAll());
	}

	@Override
	public UserDto addUser(UserDto userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return UserConverter.entityToDto(userDao.create(user));
	}

	@Override
	public UserDto getUserById(Long id) {
		return Optional.ofNullable(UserConverter.entityToDto(userDao.get(id))).orElse(new UserDto());
	}

	@Override
	public void deleteUserById(Long id) {
		userDao.delete(userDao.get(id));
		log.info("User successfully deleted");
	}

	@Override
	public void updateUser(Long id, UserDto userDto) {
		User existingUser = Optional.ofNullable(userDao.get(id)).orElse(new User());
		existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		existingUser.setEmail(userDto.getEmail());
		existingUser.setName(userDto.getName());
		userDao.update(existingUser);
		log.info("User successfully updated");

	}
}