package by.runa.lib.api.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.exceptions.UserIsAlreadyExistsException;

public interface IUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id) throws EntityNotFoundException;

    void deleteUserById(Long id);

    UserDto getUserByEmail(String email) throws EntityNotFoundException;

    UserDto createUser(UserDto userDto, DepartmentDto departmentDto) throws UserIsAlreadyExistsException;

    UserDto getUserByName(String name) throws EntityNotFoundException;

    UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws EntityNotFoundException;

    Boolean checkIfUserWithThisEmailAlreadyExists(String email);

    UserDto createUserFromSocialNetworks(UserDto userDto, DepartmentDto departmentDto);

    void sendEmailToAdmin(String email, @RequestParam String text);

    void sendEmailWithNewPassword(String email) throws EntityNotFoundException;

}
