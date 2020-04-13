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

import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.service.IFeedbackService;

@RestController
@RequestMapping("/feedbacks/")
public class FeedbackController {

	private static final String ID = "{id}";

	@Autowired
	IFeedbackService feedbackService;

	@GetMapping
	public ModelAndView getAllFeedbacks() {
		ModelAndView modelAndView = new ModelAndView();
		List<FeedbackDto> feedbacks = feedbackService.getAllFeedbacks();
		modelAndView.setViewName("allfeedbacks");
		modelAndView.addObject("feedbackList", feedbacks);
		return modelAndView;
	}

	@GetMapping(value = "addfeedback")
	public ModelAndView addFeedback() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addfeedback");
		return modelAndView.addObject("dto", new FeedbackDto());
	}

	@PostMapping(value = "addfeedback")
	public ModelAndView addFeedbackSubmit(FeedbackDto feedbackDto) {
		ModelAndView modelAndView = new ModelAndView();
		FeedbackDto newfeedback = feedbackService.createFeedback(feedbackDto);
		modelAndView.setViewName("feedback");
		return modelAndView.addObject("newfeedback", newfeedback);
	}

	@PutMapping(value = ID)
	public ModelAndView updateFeedback(@PathVariable Long id, FeedbackDto feedbackDto) {
		ModelAndView modelAndView = new ModelAndView();
		FeedbackDto updatedFeedback = feedbackService.updateFeedback(id, feedbackDto);
		modelAndView.setViewName("feedback");
		return modelAndView.addObject("feedback", updatedFeedback);
	}

	@GetMapping(value = ID)
	public ModelAndView getFeedback(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			FeedbackDto feedback = feedbackService.getFeedbackById(id);
			modelAndView.setViewName("feedback");
			modelAndView.addObject("feedback", feedback);
		} catch (Exception e) {
			modelAndView.setViewName("403");
			//TODO There is no feedback with id="id"
		}
		return modelAndView;
	}

	@DeleteMapping(value = ID)
	public ModelAndView deleteFeedback(@PathVariable Long id) {
		feedbackService.deleteFeedbackById(id);
		return getAllFeedbacks();
	}
}
