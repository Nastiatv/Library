package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IDepartmentDao;
import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.RoleDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.exceptions.UserIsAlreadyExistsException;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.entities.Department;
import by.runa.lib.entities.Role;
import by.runa.lib.entities.User;
import by.runa.lib.utils.mailsender.EmailSender;
import by.runa.lib.utils.mappers.AMapper;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    @Autowired
    private EmailSender emailSender;

    public IAGenericDao<User> getUserDao() {
        return userDao;
    }

    public IAGenericDao<Role> getRoleDao() {
        return roleDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toListDto(getUserDao().getAll());
    }

    @Override
    public UserDto createUser(UserDto userDto, DepartmentDto departmentDto) throws UserIsAlreadyExistsException {
        if (Boolean.TRUE.equals(checkIfUserWithThisNameAlreadyExists(userDto))
                && Boolean.TRUE.equals(checkIfUserWithThisEmailAlreadyExists(userDto.getEmail()))) {
            throw new UserIsAlreadyExistsException();
        } else {
            User user = new User().setEmail(userDto.getEmail()).setUsername(userDto.getUsername())
                    .setDepartment(getDepartmentByName(departmentDto))
                    .setPassword(passwordEncoder.encode(userDto.getPassword()))
                    .setRoles(Collections.singletonList(getRoleDao().get(2L)));
            return userMapper.toDto(getUserDao().create(user));
        }
    }

    @Override
    public UserDto createUserFromSocialNetworks(UserDto userDto, DepartmentDto departmentDto) {
        User user = new User().setEmail(userDto.getEmail()).setUsername(userDto.getUsername())
                .setDepartment(getDepartmentByName(departmentDto))
                .setPassword(passwordEncoder.encode(userDto.getPassword()))
                .setRoles(Collections.singletonList(getRoleDao().get(2L)));
        return userMapper.toDto(getUserDao().create(user));
    }

    @Override
    public UserDto getUserById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(userMapper.toDto(getUserDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public UserDto getUserByEmail(String email) throws EntityNotFoundException {
        return Optional.ofNullable(userMapper.toDto(userDao.getByEmail(email)))
                .orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public UserDto getUserByName(String name) throws EntityNotFoundException {
        return Optional.ofNullable(userMapper.toDto(userDao.getByName(name)))
                .orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public void deleteUserById(Long id) throws EntityNotFoundException {
        getUserDao().delete(id);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto, DepartmentDto departmentDto) throws EntityNotFoundException {
        User existingUser = Optional.ofNullable(getUserDao().get(id))
                .orElseThrow(() -> new EntityNotFoundException("User"));
        Optional.ofNullable(userDto.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(userDto.getUsername()).ifPresent(existingUser::setUsername);
        if (!StringUtils.isBlank(userDto.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (!StringUtils.isBlank(departmentDto.getName())) {
            existingUser.setDepartment(getDepartmentByName(departmentDto));
        }
        getUserDao().update(existingUser);
        return userMapper.toDto(existingUser);
    }

    @Override
    public Boolean checkIfUserWithThisEmailAlreadyExists(String email) {
        return (userDao.getByEmail(email) != null);
    }

    public void sendEmailToAdmin(String email, String text) {
        try {
            emailSender.sendEmailToAdmin(email, text);
        } catch (MessagingException e) {
            log.info("Mail not sent!");
        }
    }

    @Override
    public UserDto setRoles(Long id, RoleDto roleDto) {
        return userMapper
                .toDto(userDao.get(id).setRoles(Collections.singletonList(roleDao.getByName(roleDto.getName()))));
    }

    public void sendEmailWithNewPassword(String email) throws EntityNotFoundException {
        String newPassword = generateCommonLangPassword();
        UserDto userDto = getUserByEmail(email);
        userDto.setPassword(newPassword);
        DepartmentDto depDto = new DepartmentDto().setName("");
        updateUser(userDto.getId(), userDto, depDto);
        try {
            emailSender.sendEmailToUserWithNewPassword(newPassword, userDto);
        } catch (MessagingException e) {
            log.info("Mail not sent!");
        }
    }

    private Boolean checkIfUserWithThisNameAlreadyExists(UserDto userDto) {
        return (userDao.getByName(userDto.getUsername()) != null);
    }

    private Department getDepartmentByName(DepartmentDto departmentDto) {
        return departmentDao.getByName(departmentDto.getName());
    }

    private String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(totalChars);
        List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
}
