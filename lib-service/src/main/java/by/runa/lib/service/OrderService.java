package by.runa.lib.service;

import java.time.LocalDateTime;
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
import by.runa.lib.entities.Order;
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
	public OrderDto createOrder(Long BookId, String userName) {
		Order order = new Order();
		order.setBook(bookDao.get(BookId));
		order.setUser(userDao.getByName(userName));
		order.setOrderDate(LocalDateTime.now());
		order.setDueDate(order.getOrderDate().plusDays(10));
		order.setProlonged(false);
		order.setFinished(false);
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
	public OrderDto updateOrder(Long id, OrderDto orderDto) {
		Order existingOrder = Optional.ofNullable(orderDao.get(id)).orElse(new Order());
		existingOrder.setBook(orderDto.getBook());
		existingOrder.setUser(orderDto.getUser());
		existingOrder.setOrderDate(orderDto.getOrderDate());
		existingOrder.setDueDate(orderDto.getDueDate());
		existingOrder.setProlonged(orderDto.isProlonged());
		existingOrder.setFinished(orderDto.isFinished());
		orderDao.update(existingOrder);
		return orderMapper.toDto(existingOrder);

	}
}