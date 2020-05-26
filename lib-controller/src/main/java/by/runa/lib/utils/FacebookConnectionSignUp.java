package by.runa.lib.utils;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.service.IUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;

@Component
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private IUserService userService;

    @Override
    public String execute(Connection<?> connection) {

        Facebook facebook = (Facebook) connection.getApi();
        String[] arr = { "id", "email", "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, arr);
        if (Boolean.TRUE.equals(userService.checkIfUserWithThisEmailAlreadyExists(userProfile.getEmail()))) {
            return userProfile.getFirstName() + StringUtils.SPACE + userProfile.getLastName();
        } else {
            UserDto userDto = new UserDto();
            DepartmentDto depDto = new DepartmentDto();
            depDto.setName("default");
            userDto.setId(Long.parseLong(userProfile.getId(), 10));
            userDto.setUsername(userProfile.getFirstName() + StringUtils.SPACE + userProfile.getLastName());
            userDto.setPassword(userProfile.getId());
            userDto.setEmail(userProfile.getEmail());
            userService.createUserFromSocialNetworks(userDto, depDto);
            return userDto.getUsername();
        }
    }
}
