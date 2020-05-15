package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;

public interface IUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id) throws EntityNotFoundException;

    void deleteUserById(Long id);

    UserDto getUserByEmail(String email) throws EntityNotFoundException;

    UserDto createUser(UserDto userDto, DepartmentDto departmentDto);

    UserDto getUserByName(String name) throws EntityNotFoundException;

    UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws EntityNotFoundException;

}
