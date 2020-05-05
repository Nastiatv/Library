package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.exceptions.NoBooksAvailableException;

public interface IOrderService {

	List<OrderDto> getAllOrders();

	OrderDto getOrderById(Long id);

	void deleteOrderById(Long id);

	OrderDto createOrder(Long bookId, String userName) throws NoBooksAvailableException;

	OrderDto closeOrder(Long id) throws Exception;

	OrderDto prolongOrder(Long id) throws Exception;

}
