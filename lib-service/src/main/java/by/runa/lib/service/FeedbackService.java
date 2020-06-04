package by.runa.lib.service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IFeedbackDao;
import by.runa.lib.api.dto.BookDto;
import by.runa.lib.api.dto.FeedbackDto;
import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.exceptions.EntityNotFoundException;
import by.runa.lib.api.service.IBookService;
import by.runa.lib.api.service.IFeedbackService;
import by.runa.lib.api.service.IOrderService;
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
    private IOrderService orderService;

    @Autowired
    private AMapper<Order, OrderDto> orderMapper;

    @Autowired
    private IBookService bookService;

    @Autowired
    private AMapper<Book, BookDto> bookMapper;

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
    public FeedbackDto createFeedback(FeedbackDto feedbackDto, Long orderId) throws EntityNotFoundException {
        Order order = orderMapper.toEntity(orderService.getOrderById(orderId));
        Feedback feedback = new Feedback().setBook(order.getBook()).setUser(order.getUser())
                .setRating(feedbackDto.getRating()).setUserName(order.getUser().getUsername())
                .setComment(feedbackDto.getComment());
        getFeedbackDao().create(feedback);
        countAndSetAvgRatingForBook(order.getBook().getId());
        return feedbackMapper.toDto(feedback);
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(feedbackMapper.toDto(getFeedbackDao().get(id)))
                .orElseThrow(() -> new EntityNotFoundException(FEEDBACK));
    }

    @Override
    public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) throws EntityNotFoundException {
        Feedback existingFeedback = Optional.ofNullable(getFeedbackDao().get(id))
                .orElseThrow(() -> new EntityNotFoundException(FEEDBACK));
        existingFeedback.setRating(feedbackDto.getRating()).setComment(feedbackDto.getComment());
        getFeedbackDao().update(existingFeedback);
        countAndSetAvgRatingForBook(getBookIdByFeedbackId(id));
        return feedbackMapper.toDto(existingFeedback);
    }

    @Override
    public void deleteFeedbackById(Long id) throws EntityNotFoundException {
        Long bookId = getBookIdByFeedbackId(id);
        getFeedbackDao().delete(getFeedbackDao().get(id));
        countAndSetAvgRatingForBook(bookId);
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

    private Long getBookIdByFeedbackId(Long id) {
        return getFeedbackDao().get(id).getBook().getId();
    }

    private void countAndSetAvgRatingForBook(Long bookId) throws EntityNotFoundException {
        Book book = bookMapper.toEntity(bookService.getBookById(bookId));
        List<Feedback> feedbacks = book.getFeedbacks();
        double num = feedbacks.size();
        int sum = book.getFeedbacks().stream().mapToInt(Feedback::getRating).sum();
        if (!CollectionUtils.isEmpty(feedbacks)) {
            book.setRating(sum / num);
        }
    }
}