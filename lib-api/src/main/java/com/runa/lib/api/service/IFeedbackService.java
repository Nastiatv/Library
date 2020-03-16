package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.FeedbackDto;

public interface IFeedbackService {

	List<FeedbackDto> getAllFeedbacks();

	FeedbackDto addFeedback(FeedbackDto dto);

	FeedbackDto getFeedbackById(Long id);

	void deleteFeedbackById(Long id);

	void updateFeedback(Long id, FeedbackDto dto);

}