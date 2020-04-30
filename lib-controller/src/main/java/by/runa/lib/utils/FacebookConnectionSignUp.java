package by.runa.lib.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.service.IUserService;

@Component
public class FacebookConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private IUserService userService;

	@Override
	public String execute(Connection<?> connection) {

		Facebook facebook = (Facebook) connection.getApi();
		String[] arr = { "id", "email", "first_name", "last_name" };
		User userProfile = facebook.fetchObject("me", User.class, arr);

		//TODO if exists user email
		
		UserDto user = new UserDto();
		DepartmentDto dep = new DepartmentDto();
		dep.setName("QA");
		user.setUsername(userProfile.getFirstName() + " " + userProfile.getLastName());
		user.setPassword(userProfile.getId());
		user.setEmail(userProfile.getEmail());
		user.setDepartmentName("QA");
		userService.createUser(user, dep);
		return user.getUsername();
	}
}
