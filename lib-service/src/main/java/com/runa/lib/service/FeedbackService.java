package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.dao.IFeedbackDao;
import com.runa.lib.api.dto.FeedbackDto;
import com.runa.lib.api.service.IFeedbackService;
import com.runa.lib.entities.Feedback;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class FeedbackService implements IFeedbackService {

	@Autowired
	private IFeedbackDao feedbackDao;

	@Override
	public List<FeedbackDto> getAllFeedbacks() {
		return FeedbackDto.convertList(feedbackDao.getAll());
	}

	@Override
	public FeedbackDto addFeedback(FeedbackDto feedbackDto) {
		Feedback feedback = new Feedback();
		feedback.getBook().setId(feedbackDto.getBookId());
		feedback.getUser().setId(feedbackDto.getUserId());
		feedback.setRating(feedbackDto.getRating());
		feedback.setUserName(feedbackDto.getUserName());
		feedback.setComment(feedbackDto.getComment());
		return FeedbackDto.entityToDto(feedbackDao.create(feedback));
	}

	@Override
	public FeedbackDto getFeedbackById(Long id) {
		return Optional.ofNullable(FeedbackDto.entityToDto(feedbackDao.get(id))).orElse(new FeedbackDto());
	}

	@Override
	public void deleteFeedbackById(Long id) {
		feedbackDao.delete(feedbackDao.get(id));
		log.info("Feedback successfully deleted");
	}

	@Override
	public void updateFeedback(Long id, FeedbackDto feedbackDto) {
		Feedback existingFeedback = Optional.ofNullable(feedbackDao.get(id)).orElse(new Feedback());
		existingFeedback.setRating(feedbackDto.getRating());
		existingFeedback.getBook().setId(feedbackDto.getBookId());
		existingFeedback.getUser().setId(feedbackDto.getUserId());
		existingFeedback.setComment(feedbackDto.getComment());
		existingFeedback.setUserName(feedbackDto.getUserName());
		feedbackDao.update(existingFeedback);
		log.info("Feedback successfully updated");

	}
}