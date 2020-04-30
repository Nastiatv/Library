package by.runa.lib.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.utils.ImgFileUploader;

@RestController
@RequestMapping("/users/")
public class UserController {

	private Long principalId;

	@Autowired
	IUserService userService;

	@Autowired
	IDepartmentService departmentService;

	@Autowired
	ImgFileUploader imgFileUploader;

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
		List<DepartmentDto> departments = departmentService.getAllDepartments();
		modelAndView.addObject("departmentsList", departments);
		modelAndView.setViewName("adduser");
		modelAndView.addObject("departmentdto", new DepartmentDto());
		return modelAndView.addObject("dto", new UserDto());
	}

	@PostMapping(value = "adduser")
	public ModelAndView addUserSubmit(UserDto userDto, DepartmentDto departmentDto,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto newuser = userService.createUser(userDto, departmentDto);
		try {
			imgFileUploader.createOrUpdate(userDto, file);
			modelAndView.addObject("user", newuser);
			modelAndView.setViewName("oneuser");
			UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(newuser, null);
			SecurityContextHolder.getContext().setAuthentication(token);
			
			} catch (IOException e) {
			modelAndView.setViewName("403");
		}
		return modelAndView;
	}

	@GetMapping("{id}")
	public ModelAndView getUser(Principal principal) {
		final String currentUser = principal.getName();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("oneuser");
		try {
			principalId = userService.getUserByName(currentUser).getId();
			UserDto user = userService.getUserById(principalId);
			modelAndView.addObject("user", user);
		} catch (Exception e) {
			modelAndView.setViewName("403");
			// TODO There is no user with id="id"
		}
		return modelAndView;
	}

	@GetMapping("edit/{id}")
	public ModelAndView getUserEditForm(Principal principal) {
		final String currentUser = principal.getName();
		ModelAndView modelAndView = new ModelAndView();
		try {
			principalId = userService.getUserByName(currentUser).getId();
			UserDto user = userService.getUserById(principalId);
			List<DepartmentDto> departments = departmentService.getAllDepartments();
			modelAndView.addObject("departmentsList", departments);
			modelAndView.setViewName("updateuser");
			modelAndView.addObject("user", user);
			modelAndView.addObject("departmentdto", new DepartmentDto());
			modelAndView.addObject("dto", new UserDto());
		} catch (Exception e) {
			modelAndView.setViewName("403");
			// TODO There is no user with id="id"
		}
		return modelAndView;
	}

	@PostMapping("edit/{id}")
	public ModelAndView saveUserChanges(UserDto userDto, DepartmentDto departmentDto,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto userUpdated = userService.updateUser(principalId, userDto, departmentDto);
		try {
			imgFileUploader.createOrUpdate(userDto, file);
			modelAndView.addObject("user", userUpdated);
			modelAndView.setViewName("oneuser");
		} catch (IOException e) {
			modelAndView.setViewName("403");
		}
		return modelAndView;
	}
}
