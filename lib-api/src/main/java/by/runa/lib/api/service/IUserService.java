package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;

public interface IUserService {

	List<UserDto> getAllUsers();

	UserDto getUserById(Long id) throws Exception;

	void deleteUserById(Long id);

	UserDto updateUser(Long id, UserDto dto);

	UserDto getUserByEmail(String email) throws Exception;

	UserDto createUser(UserDto userDto, DepartmentDto departmentDto);

	UserDto getUserByName(String name) throws Exception;

}
