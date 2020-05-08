package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.exceptions.NoUserWithThisIdException;

public interface IUserService {

	List<UserDto> getAllUsers();

	UserDto getUserById(Long id) throws NoUserWithThisIdException;

	void deleteUserById(Long id);

	UserDto getUserByEmail(String email) throws NoUserWithThisIdException;

	UserDto createUser(UserDto userDto, DepartmentDto departmentDto);

	UserDto getUserByName(String name) throws NoUserWithThisIdException;

	UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws NoUserWithThisIdException;

}
