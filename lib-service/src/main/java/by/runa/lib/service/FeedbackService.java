package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.Feedback;
import by.runa.lib.entities.Order;
import by.runa.lib.utils.mappers.AMapper;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedbackService implements IFeedbackService {

    private static final String FEEDBACK = "Feedback";

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
        Order order = orderDao.get(orderId);
        Feedback feedback = new Feedback().setBook(order.getBook()).setUser(order.getUser())
                .setRating(feedbackDto.getRating()).setUserName(order.getUser().getUsername())
                .setComment(feedbackDto.getComment());
        getFeedbackDao().create(feedback);
        countAvgRatingForBook(order.getBook().getId());
        return feedbackMapper.toDto(feedback);
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(feedbackMapper.toDto(getFeedbackDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException(FEEDBACK));
    }

    @Override
    public void deleteFeedbackById(Long id) {
        Long bookId = getBookByFeedbackId(id);
        getFeedbackDao().delete(getFeedbackDao().get(bookId));
        countAvgRatingForBook(bookId);
    }

    @Override
    public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) throws EntityNotFoundException {
        Feedback existingFeedback = Optional.ofNullable(getFeedbackDao().get(id))
                .orElseThrow(() -> new EntityNotFoundException(FEEDBACK));
        existingFeedback.setRating(feedbackDto.getRating()).setComment(feedbackDto.getComment());
        getFeedbackDao().update(existingFeedback);
        countAvgRatingForBook(getBookByFeedbackId(id));
        return feedbackMapper.toDto(existingFeedback);
    }

    @Override
    public List<FeedbackDto> getAllFeedbacksByBookId(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(feedbackMapper.toListDto(feedbackDao.getAllFeedbacksByBookId(id)))
                .orElseThrow(() -> new EntityNotFoundException(FEEDBACK));
    }

    @Override
    public List<FeedbackDto> getAllFeedbacksByUserId(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(feedbackMapper.toListDto(feedbackDao.getAllFeedbacksByUserId(id)))
                .orElseThrow(() -> new EntityNotFoundException(FEEDBACK));
    }

    private Long getBookByFeedbackId(Long id) {
        return getFeedbackDao().get(id).getBook().getId();
    }

    private void countAvgRatingForBook(Long bookId) {
        Book book = bookDao.get(bookId);
        List<Feedback> feedbacks = book.getFeedbacks();
        double num = feedbacks.size();
        int sum = feedbacks.stream().mapToInt(Feedback::getRating).sum();
        if (!CollectionUtils.isEmpty(feedbacks)) {
            book.setRating(sum / num);
        }
    }
}