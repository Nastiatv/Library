package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.FeedbackDto;

public interface IFeedbackService {

	List<FeedbackDto> getAllFeedbacks();

	FeedbackDto getFeedbackById(Long id);

	void deleteFeedbackById(Long id);

	FeedbackDto updateFeedback(Long id, FeedbackDto dto);

	FeedbackDto createFeedback(FeedbackDto feedbackDto);

}