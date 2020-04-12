package com.runa.lib.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.converters.OrderConverter;
import com.runa.lib.api.dao.IOrderDao;
import com.runa.lib.api.dto.OrderDto;
import com.runa.lib.api.service.IOrderService;
import com.runa.lib.entities.Order;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class OrderService implements IOrderService {

	@Autowired
	private IOrderDao orderDao;

	@Override
	public List<OrderDto> getAllOrders() {
		return OrderConverter.convertList(orderDao.getAll());
	}

	@Override
	public OrderDto makeOrder(OrderDto orderDto) {
		Order order = new Order();
		order.getBook().setId(orderDto.getBookId());
		order.getUser().setId(orderDto.getUserId());
		order.setOrderDate(LocalDateTime.now());
		order.setDueDate(order.getOrderDate().plusDays(10));
		order.setProlongation(false);
		order.setFinished(false);
		return OrderConverter.entityToDto(orderDao.create(order));
	}

	@Override
	public OrderDto getOrderById(Long id) {
		return Optional.ofNullable(OrderConverter.entityToDto(orderDao.get(id))).orElse(new OrderDto());
	}

	@Override
	public void deleteOrderById(Long id) {
		orderDao.delete(orderDao.get(id));
		log.info("Order successfully deleted");
	}

	@Override
	public void updateOrder(Long id, OrderDto orderDto) {
		Order existingOrder = Optional.ofNullable(orderDao.get(id)).orElse(new Order());
		existingOrder.getBook().setId(orderDto.getBookId());
		existingOrder.getUser().setId(orderDto.getUserId());
		existingOrder.setOrderDate(orderDto.getOrderDate());
		existingOrder.setDueDate(orderDto.getDueDate());
		existingOrder.setProlongation(orderDto.isProlongation());
		existingOrder.setFinished(orderDto.isFinished());
		orderDao.update(existingOrder);
		log.info("Order successfully updated");

	}
}