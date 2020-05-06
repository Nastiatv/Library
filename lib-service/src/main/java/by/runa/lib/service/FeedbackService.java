package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.entities.Feedback;
import by.runa.lib.exceptions.Rating;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class FeedbackService implements IFeedbackService {

	@Autowired
	private IFeedbackDao feedbackDao;
	
	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private AMapper<Feedback, FeedbackDto> feedbackMapper;

	@Override
	public List<FeedbackDto> getAllFeedbacks() {
		return feedbackMapper.toListEntities(feedbackDao.getAll());
	}

	@Override
	public FeedbackDto createFeedback(FeedbackDto feedbackDto, Long id) {
		Feedback feedback = new Feedback();
		feedback.setId(id);
		feedback.setBook(orderDao.get(id).getBook());
		feedback.setUser(orderDao.get(id).getUser());
		feedback.setRating(Rating.findRatingFromInt((feedbackDto.getRating())));
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
	public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) {
		Feedback existingFeedback = Optional.ofNullable(feedbackDao.get(id)).orElse(new Feedback());
		existingFeedback.setRating(Rating.findRatingFromInt(feedbackDto.getRating()));
		existingFeedback.setComment(feedbackDto.getComment());
		feedbackDao.update(existingFeedback);
		return feedbackMapper.toDto(existingFeedback);

	}
}