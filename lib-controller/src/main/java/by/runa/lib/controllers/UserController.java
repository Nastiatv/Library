package by.runa.lib.controllers;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.api.service.IOrderService;
import by.runa.lib.api.service.IRoleService;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.utils.ImgFileUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/users/")
@Secured({ "ROLE_ADMIN", "ROLE_USER" })
public class UserController {

    private Long principalId;
    private static final String GENERAL_CHANGES_SAVED = "general/changesSaved";
    private static final String ERRORS = "errors/errors";
    private static final String MESSAGE = "message";
    private static final String USER = "user";

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IOrderService orderService;

    @Autowired
    ImgFileUploader imgFileUploader;

    @GetMapping("my/{id}")
    public ModelAndView getMyProfile(Principal principal) {
        final String currentUser = principal.getName();
        ModelAndView modelAndView = new ModelAndView();
        try {
            UserDto userDto = userService.getUserByEmail(currentUser);
            modelAndView.addObject(USER, userDto);
            principalId = userDto.getId();
            modelAndView.addObject("ListOrders", orderService.getAllOrdersByUserId(principalId));
            modelAndView.setViewName("myprofile");
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    @GetMapping("myedit/{id}")
    public ModelAndView getMyEditForm(Principal principal) {
        final String currentUser = principal.getName();
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("departmentsList", departmentService.getAllDepartments());
            modelAndView.addObject(USER, userService.getUserByEmail(currentUser));
            modelAndView.addObject("departmentdto", new DepartmentDto());
            modelAndView.addObject("dto", new UserDto());
            modelAndView.setViewName("updateuser");
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    @PostMapping("myedit/{id}")
    public ModelAndView saveMyChanges(UserDto userDto, DepartmentDto departmentDto,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            imgFileUploader.createOrUpdateUserAvatar(userDto, file);
            modelAndView.addObject(USER, userService.updateUser(principalId, userDto, departmentDto));
            modelAndView.setViewName(GENERAL_CHANGES_SAVED);
        } catch (IOException | EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }
  
    private void returnViewNameWithError(ModelAndView modelAndView, EntityNotFoundException e) {
        modelAndView.setViewName(ERRORS);
        modelAndView.addObject(MESSAGE, e.getMessage());
    }
}
