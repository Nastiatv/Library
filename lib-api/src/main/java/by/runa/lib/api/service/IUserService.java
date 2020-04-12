package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.UserDto;

public interface IUserService {

	List<UserDto> getAllUsers();

	UserDto addUser(UserDto dto);

	UserDto getUserById(Long id);

	void deleteUserById(Long id);

	UserDto updateUser(Long id, UserDto dto);

}
