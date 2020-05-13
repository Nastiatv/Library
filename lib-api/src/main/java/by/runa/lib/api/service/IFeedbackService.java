package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.exceptions.NoFeedbackWithThisIdException;

public interface IFeedbackService {

	List<FeedbackDto> getAllFeedbacks();

	FeedbackDto getFeedbackById(Long id) throws NoFeedbackWithThisIdException;

	void deleteFeedbackById(Long id);

	FeedbackDto updateFeedback(Long id, FeedbackDto dto) throws NoFeedbackWithThisIdException;

	FeedbackDto createFeedback(FeedbackDto feedbackDto, Long id);

	List<FeedbackDto> getAllFeedbacksByBookId(Long bookId) throws NoFeedbackWithThisIdException;

	List<FeedbackDto> getAllFeedbacksByUserId(Long principalId) throws NoFeedbackWithThisIdException ;

}