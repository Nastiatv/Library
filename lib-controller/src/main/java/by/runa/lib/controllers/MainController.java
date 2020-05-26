package by.runa.lib.controllers;

import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @Autowired
    IUserService userService;

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
