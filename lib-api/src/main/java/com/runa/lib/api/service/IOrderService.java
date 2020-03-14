package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.OrderDto;

public interface IOrderService {

	List<OrderDto> getAllOrders();

	OrderDto addOrder(OrderDto dto);

	OrderDto getOrderById(Long id);

	void deleteOrderById(Long id);

	void updateOrder(Long id, OrderDto dto);

}
