package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.UserDto;

public interface IUserService {

	List<UserDto> getAllUsers();

	UserDto addUser(UserDto dto);

	UserDto getUserById(Long id);

	void deleteUserById(Long id);

	void updateUser(Long id, UserDto dto);

}
