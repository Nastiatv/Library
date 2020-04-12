package by.runa.lib.controllers;

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
import org.springframework.web.servlet.ModelAndView;

import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.service.IFeedbackService;

@RestController
@RequestMapping(value = "/feedbacks/")
public class FeedbackController {

	private static final String ID = "{id}";

	@Autowired
	IFeedbackService feedbackService;

	@GetMapping
	public ModelAndView getAllFeedbacks() {
		ModelAndView modelAndView = new ModelAndView();
		List<FeedbackDto> feedbacks = feedbackService.getAllFeedbacks();
		modelAndView.setViewName("allfeedbacks");
		modelAndView.addObject("feedbacksList", feedbacks);
		return modelAndView;
	}

	public List<FeedbackDto> getAllFeedback() {
		return feedbackService.getAllFeedbacks();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public FeedbackDto addFeedback(@RequestBody FeedbackDto dto) {
		return feedbackService.addFeedback(dto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateFeedback(@PathVariable Long id, @RequestBody FeedbackDto dto) {
		feedbackService.updateFeedback(id, dto);
	}

	@GetMapping(value = ID)
	public FeedbackDto getFeedback(@PathVariable Long id) {
		return feedbackService.getFeedbackById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteFeedback(@PathVariable Long id) {
		feedbackService.deleteFeedbackById(id);
	}
}
