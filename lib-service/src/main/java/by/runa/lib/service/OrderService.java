package by.runa.lib.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.runa.lib.api.dao.IBookDao;
import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.api.dao.IUserDao;
import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.api.mappers.AMapper;
import by.runa.lib.api.service.IOrderService;
import by.runa.lib.entities.Book;
import by.runa.lib.entities.Order;
import by.runa.lib.exceptions.IsAlreadyClosedException;
import by.runa.lib.exceptions.IsAlreadyProlongedException;
import by.runa.lib.exceptions.NoBooksAvailableException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class OrderService implements IOrderService {

	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private AMapper<Order, OrderDto> orderMapper;

	@Override
	public List<OrderDto> getAllOrders() {
		return orderMapper.toListEntities(orderDao.getAll());
	}

	@Override
	public OrderDto createOrder(Long bookId, String userName) throws NoBooksAvailableException {
		Order order = new Order();
		Book book = bookDao.get(bookId);
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

		return orderMapper.toDto(orderDao.create(order));
	}

	@Override
	public OrderDto getOrderById(Long id) {
		return Optional.ofNullable(orderMapper.toDto(orderDao.get(id))).orElse(new OrderDto());
	}

	@Override
	public void deleteOrderById(Long id) {
		orderDao.delete(orderDao.get(id));
		log.info("Order successfully deleted");
	}

	@Override
	public OrderDto prolongOrder(Long id) throws Exception {
		Order existingOrder = Optional.ofNullable(orderDao.get(id)).orElseThrow(Exception::new);
		if (existingOrder.isProlonged()) {
			throw new IsAlreadyProlongedException();
		} else {
			existingOrder.setProlonged(true);
			existingOrder.setDueDate(LocalDate.now().plusDays(10));
			orderDao.update(existingOrder);
		}
		return orderMapper.toDto(existingOrder);
	}

	@Override
	public OrderDto closeOrder(Long id) throws Exception {
		Order existingOrder = Optional.ofNullable(orderDao.get(id)).orElseThrow(Exception::new);
		if (existingOrder.isFinished()) {
			throw new IsAlreadyClosedException();
		} else {
			existingOrder.setFinished(true);
			bookDao.get(existingOrder.getBook().getId()).setQuantityAvailable(bookDao.get(existingOrder.getBook().getId()).getQuantityAvailable()+1);
			orderDao.update(existingOrder);
		}
		return orderMapper.toDto(existingOrder);
	}
}