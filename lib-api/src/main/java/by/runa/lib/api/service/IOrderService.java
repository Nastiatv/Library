package by.runa.lib.api.service;

import java.util.List;

import by.runa.lib.api.dto.OrderDto;
import by.runa.lib.exceptions.IsAlreadyClosedException;
import by.runa.lib.exceptions.IsAlreadyProlongedException;
import by.runa.lib.exceptions.NoBooksAvailableException;
import by.runa.lib.exceptions.NoOrderWithThisIdException;

public interface IOrderService {

	List<OrderDto> getAllOrders();

	OrderDto getOrderById(Long id) throws NoOrderWithThisIdException;

	void deleteOrderById(Long id);

	OrderDto createOrder(Long bookId, String userName) throws NoBooksAvailableException;

	OrderDto closeOrder(Long id) throws IsAlreadyClosedException, NoOrderWithThisIdException;

	OrderDto prolongOrder(Long id) throws NoOrderWithThisIdException, IsAlreadyClosedException, IsAlreadyProlongedException;

	List<OrderDto> getAllOrdersByUserId(Long id) throws NoOrderWithThisIdException;

}
