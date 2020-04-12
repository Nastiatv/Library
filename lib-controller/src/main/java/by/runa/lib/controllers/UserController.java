package by.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.service.IUserService;

@RestController
@RequestMapping("/users/")
public class UserController {

	private static final String ID = "{id}";

	@Autowired
	IUserService userService;

	@GetMapping
	public ModelAndView getAllUsers() {
		ModelAndView modelAndView = new ModelAndView();
		List<UserDto> users = userService.getAllUsers();
		modelAndView.setViewName("allusers");
		modelAndView.addObject("userList", users);
		return modelAndView;
	}

	@GetMapping(value = "adduser")
	public ModelAndView addUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("adduser");
		return modelAndView.addObject("dto", new UserDto());
	}

	@PostMapping(value = "adduser")
	public ModelAndView addUserSubmit(UserDto userDto) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto newuser = userService.addUser(userDto);
		modelAndView.setViewName("user");
		return modelAndView.addObject("newuser", newuser);
	}

	@PutMapping(value = ID)
	public ModelAndView updateUser(@PathVariable Long id, UserDto userDto) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto updatedUser = userService.updateUser(id, userDto);
		modelAndView.setViewName("user");
		return modelAndView.addObject("user", updatedUser);
	}

	@GetMapping(value = ID)
	public ModelAndView getUser(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			UserDto user = userService.getUserById(id);
			modelAndView.setViewName("user");
			modelAndView.addObject("user", user);
		} catch (Exception e) {

			modelAndView.setViewName("403");
		}
		return modelAndView;
	}

	@DeleteMapping(value = ID)
	public ModelAndView deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return getAllUsers();
	}
}
