package by.runa.lib.api.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.exceptions.UserIsAlreadyExistsException;

public interface IUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id) throws EntityNotFoundException;

    void deleteUserById(Long id) throws EntityNotFoundException;

    UserDto getUserByEmail(String email) throws EntityNotFoundException;

    UserDto createUser(UserDto userDto, DepartmentDto departmentDto)
            throws UserIsAlreadyExistsException, EntityNotFoundException;

    UserDto getUserByName(String name) throws EntityNotFoundException;

    UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws EntityNotFoundException;

    Boolean checkIfUserWithThisEmailAlreadyExists(String email);

    UserDto createUserFromSocialNetworks(UserDto userDto, DepartmentDto departmentDto);

    void sendEmailToAdmin(String email, @RequestParam String text);

    void sendEmailWithNewPassword(String email) throws EntityNotFoundException;

    UserDto setRoles(Long id, RoleDto roleDto) throws EntityNotFoundException;

    UserDto setOrUpdateUserAvatar(Long id, String base64Encoded) throws EntityNotFoundException;

}
