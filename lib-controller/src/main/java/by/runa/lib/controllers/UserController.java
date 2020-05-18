package by.runa.lib.controllers;

import by.runa.lib.api.dao.IRoleDao;
import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.api.service.IOrderService;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.utils.ImgFileUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {

    private Long principalId;
    private static final String ERRORS = "errors/errors";
    private static final String MESSAGE = "message";
    private static final String USER = "user";

    @Autowired
    IUserService userService;

    @Autowired
    IRoleDao roleDao;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IOrderService orderService;

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

    @GetMapping("{id}")
    public ModelAndView getMyUsers(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("oneuser");
        try {
            UserDto user = userService.getUserById(id);
            modelAndView.addObject(USER, user);
            List<OrderDto> listorders = orderService.getAllOrdersByUserId(user.getId());
            modelAndView.addObject("ListOrders", listorders);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
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
        userService.createUser(userDto, departmentDto);
        try {
            imgFileUploader.createOrUpdate(userDto, file);
            modelAndView.setViewName("general/login");
        } catch (IOException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("my/{id}")
    public ModelAndView getUser(Principal principal) {
        final String currentUser = principal.getName();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("myprofile");
        try {
            principalId = userService.getUserByName(currentUser).getId();
            UserDto user = userService.getUserById(principalId);
            modelAndView.addObject(USER, user);
            List<OrderDto> listorders = orderService.getAllOrdersByUserId(principalId);
            modelAndView.addObject("ListOrders", listorders);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
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
            modelAndView.addObject(USER, user);
            modelAndView.addObject("departmentdto", new DepartmentDto());
            modelAndView.addObject("dto", new UserDto());
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveUserChanges(UserDto userDto, DepartmentDto departmentDto,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            UserDto userUpdated = userService.updateUser(principalId, userDto, departmentDto);
            imgFileUploader.createOrUpdate(userDto, file);
            modelAndView.addObject(USER, userUpdated);
            modelAndView.setViewName("general/changesSaved");
        } catch (IOException | EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }
}
