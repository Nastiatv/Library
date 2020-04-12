package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.OrderDto;

public interface IOrderService {

	List<OrderDto> getAllOrders();

	OrderDto getOrderById(Long id);

	void deleteOrderById(Long id);

	void updateOrder(Long id, OrderDto dto);

	OrderDto makeOrder(OrderDto orderDto);

}
