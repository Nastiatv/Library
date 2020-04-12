package com.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runa.lib.api.dto.OrderDto;
import com.runa.lib.api.service.IOrderService;

@RestController
@RequestMapping(value = "/orders/")
public class OrderController {

	private static final String ID = "{id}";

	@Autowired
	IOrderService orderService;

	@GetMapping
	public List<OrderDto> getAllOrder() {
		return orderService.getAllOrders();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDto addOrder(@RequestBody OrderDto dto) {
		return orderService.makeOrder(dto);
	}

	@GetMapping(value = ID)
	public OrderDto getOrder(@PathVariable Long id) {
		return orderService.getOrderById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteOrder(@PathVariable Long id) {
		orderService.deleteOrderById(id);
	}
}
