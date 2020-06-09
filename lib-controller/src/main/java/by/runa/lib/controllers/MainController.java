package by.runa.lib.controllers;

import by.runa.lib.api.dto.DepartmentDto;
import by.runa.lib.api.dto.UserDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.exceptions.UserIsAlreadyExistsException;
import by.runa.lib.api.service.IDepartmentService;
import by.runa.lib.api.service.IUserService;
import by.runa.lib.utils.ImgFileUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {

    @Autowired
    IUserService userService;

    @Autowired
    ImgFileUploader imgFileUploader;

    @Autowired
    IDepartmentService departmentService;

    @GetMapping("/users/adduser")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("departmentsList", departmentService.getAllDepartments());
        modelAndView.setViewName("adduser");
        modelAndView.addObject("departmentdto", new DepartmentDto());
        return modelAndView.addObject("dto", new UserDto());
    }

    @PostMapping("/users/adduser")
    public ModelAndView addUserSubmit(UserDto userDto, DepartmentDto departmentDto,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            imgFileUploader.createOrUpdateUserAvatar(userService.createUser(userDto, departmentDto), file);
            modelAndView.setViewName("general/login");
        } catch (IOException | EntityNotFoundException | UserIsAlreadyExistsException e) {
            modelAndView.setViewName("errors/errors");
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/thankU")
    public ModelAndView mailToAdminFeedback(@RequestParam String email, @RequestParam String message) {
        ModelAndView modelAndView = new ModelAndView();
        userService.sendEmailToAdmin(email, message);
        modelAndView.setViewName("general/thankU");
        return modelAndView;
    }

    @GetMapping("/newPasswordSent")
    public ModelAndView mailToUserNewPassword(@RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            userService.sendEmailWithNewPassword(email);
            modelAndView.setViewName("general/thankU");
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName("errors/errors");
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

}
