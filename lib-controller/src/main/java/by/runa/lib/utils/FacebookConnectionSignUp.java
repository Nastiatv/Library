package by.runa.lib.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
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
		UserDto user = new UserDto();
		DepartmentDto dep = new DepartmentDto();
		dep.setName("QA");
		user.setUsername(connection.getDisplayName());
		user.setPassword("123");
		user.setEmail("nasgsdfsdf645s@mail.ru");
		user.setDepartmentName("QA");
		userService.createUser(user, dep);
		return user.getUsername();
	}
}
