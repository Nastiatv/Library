package by.runa.lib.controllers;

import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.api.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/feedbacks/")
public class FeedbackController {

    private static final String ERRORS = "errors/errors";
    private static final String MESSAGE = "message";
    private static final String FEEDBACK = "feedback";

    @Autowired
    IFeedbackService feedbackService;

    @Autowired
    IUserService userService;

    @GetMapping
    public ModelAndView getAllFeedbacks() {
        ModelAndView modelAndView = new ModelAndView();
        List<FeedbackDto> feedbacks = feedbackService.getAllFeedbacks();
        modelAndView.setViewName("allfeedbacks");
        modelAndView.addObject("feedbackList", feedbacks);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView getFeedbackById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            FeedbackDto feedback = feedbackService.getFeedbackById(id);
            modelAndView.setViewName("onefeedback");
            modelAndView.addObject(FEEDBACK, feedback);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("my")
    public ModelAndView getMyOrders(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        final String currentUser = principal.getName();
        try {
            long principalId = userService.getUserByName(currentUser).getId();
            List<FeedbackDto> feedbacklist = feedbackService.getAllFeedbacksByUserId(principalId);
            modelAndView.setViewName("allfeedbacks");
            modelAndView.addObject("feedbackList", feedbacklist);
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = "addfeedback/{id}")
    public ModelAndView addFeedback(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addfeedback");
        return modelAndView.addObject("feedbackdto", new FeedbackDto());
    }

    @PostMapping(value = "addfeedback/{id}")
    public ModelAndView addFeedbackSubmit(@PathVariable Long id, FeedbackDto feedbackDto) {
        ModelAndView modelAndView = new ModelAndView();
        FeedbackDto newfeedback = feedbackService.createFeedback(feedbackDto, id);
        modelAndView.setViewName("general/changesSaved");
        return modelAndView.addObject(FEEDBACK, newfeedback);
    }

    @GetMapping("edit/{id}")
    public ModelAndView getFeedbackEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
            modelAndView.setViewName("updatefeedback");
            modelAndView.addObject(FEEDBACK, feedbackDto);
            modelAndView.addObject("dto", new FeedbackDto());
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveFeedbackChanges(@PathVariable Long id, FeedbackDto feedbackDto) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            FeedbackDto feedbackUpdated = feedbackService.updateFeedback(id, feedbackDto);
            modelAndView.addObject(FEEDBACK, feedbackUpdated);
            modelAndView.setViewName("general/changesSaved");
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteFeedback(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
            modelAndView.addObject(FEEDBACK, feedbackDto);
            modelAndView.setViewName("deletefeedback");
            modelAndView.addObject("feedbackDto", new FeedbackDto());
        } catch (EntityNotFoundException e) {
            modelAndView.setViewName(ERRORS);
            modelAndView.addObject(MESSAGE, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("delete/{id}")
    public ModelAndView deletefeedbackSubmit(FeedbackDto feedbackDto) {
        feedbackService.deleteFeedbackById(feedbackDto.getId());
        return getAllFeedbacks();
    }
}