package com.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runa.lib.api.dto.UserDto;
import com.runa.lib.api.service.IUserService;

@RestController
@RequestMapping(value = "/users/")
public class UserController {

	private static final String ID = "{id}";

	@Autowired
	IUserService userService;

	@GetMapping
	public List<UserDto> getAllUser() {
		return userService.getAllUsers();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto addUser(@RequestBody UserDto dto) {
		return userService.addUser(dto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
		userService.updateUser(id, dto);
	}

	@GetMapping(value = ID)
	public UserDto getUser(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
	}
}
