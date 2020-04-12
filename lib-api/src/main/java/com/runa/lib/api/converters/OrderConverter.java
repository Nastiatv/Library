package com.runa.lib.api.converters;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.api.dto.OrderDto;
import com.runa.lib.entities.Order;

public class OrderConverter {

	public static List<OrderDto> convertList(List<Order> entities) {
		List<OrderDto> listDto = new ArrayList<>();
		for (Order Order : entities) {
			OrderDto dto = new OrderDto();
			dto.setId(Order.getId());
			dto.setUserId(Order.getUser().getId());
			dto.setBookId(Order.getBook().getId());
			dto.setOrderDate(Order.getOrderDate());
			dto.setDueDate(Order.getDueDate());
			dto.setFinished(Order.isFinished());
			dto.setProlongation(Order.isProlongation());
			listDto.add(dto);
		}
		return listDto;
	}

	public static OrderDto entityToDto(Order entity) {
		OrderDto dto = new OrderDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setUserId(entity.getUser().getId());
			dto.setBookId(entity.getBook().getId());
			dto.setOrderDate(entity.getOrderDate());
			dto.setDueDate(entity.getDueDate());
			dto.setFinished(entity.isFinished());
			dto.setProlongation(entity.isProlongation());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Order dtoToEntity(OrderDto dto) {
		Order entity = new Order();
		entity.setId(dto.getId());
		entity.getUser().setId(dto.getUserId());
		entity.getBook().setId(dto.getBookId());
		entity.setOrderDate(dto.getOrderDate());
		entity.setDueDate(dto.getDueDate());
		entity.setFinished(dto.isFinished());
		entity.setProlongation(dto.isProlongation());
		return entity;
	}
}
