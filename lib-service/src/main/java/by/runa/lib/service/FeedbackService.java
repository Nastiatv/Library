package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.entities.Feedback;

@Service
@Transactional
public class FeedbackService implements IFeedbackService {

	@Autowired
	private IFeedbackDao feedbackDao;

	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private AMapper<Feedback, FeedbackDto> feedbackMapper;

	@Override
	public List<FeedbackDto> getAllFeedbacks() {
		return feedbackMapper.toListDto(feedbackDao.getAll());
	}

	@Override
	public FeedbackDto createFeedback(FeedbackDto feedbackDto, Long orderId) {
		Feedback feedback = new Feedback();
		feedback.setBook(orderDao.get(orderId).getBook());
		feedback.setUser(orderDao.get(orderId).getUser());
		feedback.setRating(feedbackDto.getRating());
		feedback.setUserName(orderDao.get(orderId).getUser().getUsername());
		feedback.setComment(feedbackDto.getComment());
		feedbackDao.create(feedback);
		countAvgRatingForBook(orderDao.get(orderId).getBook().getId());
		return feedbackMapper.toDto(feedback);
	}

	@Override
	public FeedbackDto getFeedbackById(Long id) {
		return Optional.ofNullable(feedbackMapper.toDto(feedbackDao.get(id))).orElse(new FeedbackDto());
	}

	@Override
	public void deleteFeedbackById(Long id) {
		feedbackDao.delete(feedbackDao.get(id));
		countAvgRatingForBook(feedbackDao.get(id).getBook().getId());
	}

	@Override
	public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) {
		Feedback existingFeedback = Optional.ofNullable(feedbackDao.get(id)).orElse(new Feedback());
		existingFeedback.setRating(feedbackDto.getRating());
		existingFeedback.setComment(feedbackDto.getComment());
		feedbackDao.update(existingFeedback);
		countAvgRatingForBook(feedbackDao.get(id).getBook().getId());
		return feedbackMapper.toDto(existingFeedback);

	}

	private void countAvgRatingForBook(Long bookId) {
		int num = 0;
		double sum = 0;
		for (Feedback feedback : bookDao.get(bookId).getFeedbacks()) {
			sum += feedback.getRating();
			num++;
		}
		bookDao.get(bookId).setRating(sum / num);
	}

	@Override
	public List<FeedbackDto> getAllFeedbacksByBookId(Long id) {
		return feedbackMapper.toListDto(feedbackDao.getAllFeedbacksByBookId(id));
	}
}