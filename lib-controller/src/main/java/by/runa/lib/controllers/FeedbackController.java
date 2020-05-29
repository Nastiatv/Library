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
    private static final String CHANGESSAVED = "general/changesSaved";

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
            modelAndView.setViewName("onefeedback");
            modelAndView.addObject(FEEDBACK, feedbackService.getFeedbackById(id));
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    @GetMapping("my")
    public ModelAndView getMyFeedbacks(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        final String currentUser = principal.getName();
        try {
            long principalId = userService.getUserByName(currentUser).getId();
            modelAndView.setViewName("allfeedbacks");
            modelAndView.addObject("feedbackList", feedbackService.getAllFeedbacksByUserId(principalId));
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
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
        modelAndView.setViewName("general/thankU");
        return modelAndView.addObject(FEEDBACK, feedbackService.createFeedback(feedbackDto, id));
    }

    @GetMapping("edit/{id}")
    public ModelAndView getFeedbackEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("updatefeedback");
            modelAndView.addObject(FEEDBACK, feedbackService.getFeedbackById(id));
            modelAndView.addObject("dto", new FeedbackDto());
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveFeedbackChanges(@PathVariable Long id, FeedbackDto feedbackDto) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject(FEEDBACK, feedbackService.updateFeedback(id, feedbackDto));
            modelAndView.setViewName(CHANGESSAVED);
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteFeedback(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject(FEEDBACK, feedbackService.getFeedbackById(id));
            modelAndView.setViewName("deletefeedback");
            modelAndView.addObject("feedbackDto", new FeedbackDto());
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    @PostMapping("delete/{id}")
    public ModelAndView deletebookSubmit(FeedbackDto feedbackDto) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            feedbackService.deleteFeedbackById(feedbackDto.getId());
            modelAndView.setViewName(CHANGESSAVED);
        } catch (EntityNotFoundException e) {
            returnViewNameWithError(modelAndView, e);
        }
        return modelAndView;
    }

    private void returnViewNameWithError(ModelAndView modelAndView, EntityNotFoundException e) {
        modelAndView.setViewName(ERRORS);
        modelAndView.addObject(MESSAGE, e.getMessage());
    }
}