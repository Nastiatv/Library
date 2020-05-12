package by.runa.lib.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IAGenericDao;
import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IOrderService;
import by.runa.lib.api.utils.IEmailSender;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.Order;
import by.runa.lib.exceptions.IsAlreadyClosedException;
import by.runa.lib.exceptions.IsAlreadyProlongedException;
import by.runa.lib.exceptions.NoBooksAvailableException;
import by.runa.lib.exceptions.NoOrderWithThisIdException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableScheduling
@Transactional
public class OrderService implements IOrderService {

	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IEmailSender emailSender;

	@Autowired
	private AMapper<Order, OrderDto> orderMapper;

	public IAGenericDao<Order> getOrderDao() {
		return orderDao;
	}	
	
	public IAGenericDao<Book> getBookDao() {
		return bookDao;
	}
	
	@Override
	public List<OrderDto> getAllOrders() {
		return orderMapper.toListDto(getOrderDao().getAll());
	}

	@Override
	public OrderDto createOrder(Long bookId, String userName) throws NoBooksAvailableException {
		Order order = new Order();
		Book book = getBookDao().get(bookId);
		if (book.getQuantityAvailable() != 0) {
			order.setBook(book);
			book.setQuantityAvailable(book.getQuantityAvailable() - 1);
			order.setUser(userDao.getByName(userName));
			order.setOrderDate(LocalDate.now());
			order.setDueDate(order.getOrderDate().plusDays(10));
			order.setProlonged(false);
			order.setFinished(false);
		} else {
			throw new NoBooksAvailableException();
		}

		return orderMapper.toDto(getOrderDao().create(order));
	}

	@Override
	public OrderDto getOrderById(Long id) throws NoOrderWithThisIdException {
		return Optional.ofNullable(orderMapper.toDto(getOrderDao().get(id))).orElseThrow(NoOrderWithThisIdException::new);
	}

	@Override
	public List<OrderDto> getAllOrdersByUserId(Long id) throws NoOrderWithThisIdException {
		return Optional.ofNullable(orderMapper.toListDto(orderDao.getAllOrdersByUserId(id)))
				.orElseThrow(NoOrderWithThisIdException::new);
	}

	@Override
	public void deleteOrderById(Long id) {
		getOrderDao().delete(getOrderDao().get(id));
	}

	@Override
	public OrderDto prolongOrder(Long id)
			throws NoOrderWithThisIdException, IsAlreadyClosedException, IsAlreadyProlongedException {
		Order existingOrder = Optional.ofNullable(getOrderDao().get(id)).orElseThrow(NoOrderWithThisIdException::new);
		if (existingOrder.isFinished()) {
			throw new IsAlreadyClosedException();
		} else {
			if (existingOrder.isProlonged()) {
				throw new IsAlreadyProlongedException();
			} else {
				existingOrder.setProlonged(true);
				existingOrder.setDueDate(LocalDate.now().plusDays(10));
				getOrderDao().update(existingOrder);
			}
		}
		return orderMapper.toDto(existingOrder);
	}

	@Override
	public OrderDto closeOrder(Long id) throws IsAlreadyClosedException, NoOrderWithThisIdException {
		Order existingOrder = Optional.ofNullable(getOrderDao().get(id)).orElseThrow(NoOrderWithThisIdException::new);
		if (existingOrder.isFinished()) {
			throw new IsAlreadyClosedException();
		} else {
			existingOrder.setFinished(true);
			getBookDao().get(existingOrder.getBook().getId())
					.setQuantityAvailable(getBookDao().get(existingOrder.getBook().getId()).getQuantityAvailable() + 1);
			getOrderDao().update(existingOrder);
		}
		return orderMapper.toDto(existingOrder);
	}

	@Scheduled(cron = "0 17 16 * * *")
	public void checkIfOrderIsExpired() {
		for (Order order : getOrderDao().getAll()) {
			if (order.getDueDate().isBefore(LocalDate.now()) && !order.isFinished()) {
				try {
					emailSender.sendEmailsFromAdminAboutDebts(order);
				} catch (MessagingException e) {
					log.info("Mail not sent!");
				}
			}
			if (order.getDueDate().isEqual(LocalDate.now().plusDays(1)) && !order.isFinished()) {
				try {
					emailSender.sendEmailsFromAdminDueDateTomorrow(order);
				} catch (MessagingException e) {
					log.info("Mail not sent!");
				}
			}
		}
	}
}