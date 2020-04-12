package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.mappers.FeedbackMapper;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.entities.Feedback;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class FeedbackService implements IFeedbackService {

	@Autowired
	private IFeedbackDao feedbackDao;

	@Autowired
	private FeedbackMapper feedbackMapper;

	@Override
	public List<FeedbackDto> getAllFeedbacks() {
		return feedbackMapper.toListEntities(feedbackDao.getAll());
	}

	@Override
	public FeedbackDto addFeedback(FeedbackDto feedbackDto) {
		Feedback feedback = new Feedback();
		feedback.getBook().setId(feedbackDto.getBookId());
		feedback.getUser().setId(feedbackDto.getUserId());
		feedback.setRating(feedbackDto.getRating());
		feedback.setUserName(feedbackDto.getUserName());
		feedback.setComment(feedbackDto.getComment());
		return feedbackMapper.toDto(feedbackDao.create(feedback));
	}

	@Override
	public FeedbackDto getFeedbackById(Long id) {
		return Optional.ofNullable(feedbackMapper.toDto(feedbackDao.get(id))).orElse(new FeedbackDto());
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