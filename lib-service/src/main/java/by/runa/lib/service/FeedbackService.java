package by.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.exceptions.NoFeedbackWithThisIdException;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.entities.Feedback;
import by.runa.lib.utils.mappers.AMapper;

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
	
	public IAGenericDao<Feedback> getFeedbackDao() {
		return feedbackDao;
	}

	@Override
	public List<FeedbackDto> getAllFeedbacks() {
		return feedbackMapper.toListDto(getFeedbackDao().getAll());
	}

	@Override
	public FeedbackDto createFeedback(FeedbackDto feedbackDto, Long orderId) {
		Feedback feedback = new Feedback();
		feedback.setBook(orderDao.get(orderId).getBook());
		feedback.setUser(orderDao.get(orderId).getUser());
		feedback.setRating(feedbackDto.getRating());
		feedback.setUserName(orderDao.get(orderId).getUser().getUsername());
		feedback.setComment(feedbackDto.getComment());
		getFeedbackDao().create(feedback);
		countAvgRatingForBook(orderDao.get(orderId).getBook().getId());
		return feedbackMapper.toDto(feedback);
	}

	@Override
	public FeedbackDto getFeedbackById(Long id) throws NoFeedbackWithThisIdException {
		return Optional.ofNullable(feedbackMapper.toDto(getFeedbackDao().get(id)))
				.orElseThrow(NoFeedbackWithThisIdException::new);
	}

	@Override
	public void deleteFeedbackById(Long id) {
		getFeedbackDao().delete(getFeedbackDao().get(id));
		countAvgRatingForBook(getFeedbackDao().get(id).getBook().getId());
	}

	@Override
	public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) throws NoFeedbackWithThisIdException {
		Feedback existingFeedback = Optional.ofNullable(getFeedbackDao().get(id))
				.orElseThrow(NoFeedbackWithThisIdException::new);
		existingFeedback.setRating(feedbackDto.getRating());
		existingFeedback.setComment(feedbackDto.getComment());
		getFeedbackDao().update(existingFeedback);

		countAvgRatingForBook(getFeedbackDao().get(id).getBook().getId());
		return feedbackMapper.toDto(existingFeedback);
	}

	private void countAvgRatingForBook(Long bookId) {
		int num = 0;
		double sum = 0;
		for (Feedback feedback : bookDao.get(bookId).getFeedbacks()) {
			sum += feedback.getRating();
			num++;
		}
		if (num != 0) {
			bookDao.get(bookId).setRating(sum / num);
		}
	}

	@Override
	public List<FeedbackDto> getAllFeedbacksByBookId(Long id) throws NoFeedbackWithThisIdException {
		return Optional.ofNullable(feedbackMapper.toListDto(feedbackDao.getAllFeedbacksByBookId(id)))
				.orElseThrow(NoFeedbackWithThisIdException::new);
	}

	@Override
	public List<FeedbackDto> getAllFeedbacksByUserId(Long id) throws NoFeedbackWithThisIdException {
		return Optional.ofNullable(feedbackMapper.toListDto(feedbackDao.getAllFeedbacksByUserId(id)))
				.orElseThrow(NoFeedbackWithThisIdException::new);
	}
}