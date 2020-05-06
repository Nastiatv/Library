package by.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.service.IFeedbackService;

@RestController
@RequestMapping("/feedbacks/")
public class FeedbackController {

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

	@GetMapping("{id}")
	public ModelAndView getFeedbackById(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			FeedbackDto feedback = feedbackService.getFeedbackById(id);
			modelAndView.setViewName("onefeedback");
			modelAndView.addObject("feedback", feedback);
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no feedback with id="id"
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
		modelAndView.setViewName("onefeedback");
		return modelAndView.addObject("feedback", newfeedback);
	}

	@GetMapping("edit/{id}")
	public ModelAndView getFeedbackEditForm(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
			modelAndView.setViewName("updatefeedback");
			modelAndView.addObject("feedback", feedbackDto);
			modelAndView.addObject("dto", new FeedbackDto());
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no feedback with id="id"
		}
		return modelAndView;
	}

	@PostMapping("edit/{id}")
	public ModelAndView saveFeedbackChanges(FeedbackDto feedbackDto) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			FeedbackDto feedbackUpdated = feedbackService.updateFeedback(null, feedbackDto);
			modelAndView.addObject("feedback", feedbackUpdated);
			modelAndView.setViewName("changesSaved");
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
		}
		return modelAndView;
	}

	@GetMapping("delete/{id}")
	public ModelAndView deleteFeedback(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
			modelAndView.addObject("feedback", feedbackDto);
			modelAndView.setViewName("deletefeedback");
			modelAndView.addObject("feedbackDto", new FeedbackDto());
		} catch (Exception e) {
			modelAndView.setViewName("errors/403");
			// TODO There is no feedback with id="id"
		}
		return modelAndView;
	}

	@PostMapping("delete/{id}")
	public ModelAndView deletefeedbackSubmit(FeedbackDto feedbackDto) {
		feedbackService.deleteFeedbackById(feedbackDto.getId());
		return getAllFeedbacks();
	}
}