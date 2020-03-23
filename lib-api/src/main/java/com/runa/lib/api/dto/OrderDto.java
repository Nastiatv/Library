package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.runa.lib.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	private Long id;
	private Long userId;
	private Long bookId;
	private Long notificationId;
	private Date orderDate;
	private Date dueDate;
	private boolean isFinished;
	private boolean prolongation;

	public static List<OrderDto> convertList(List<Order> entities) {
		List<OrderDto> listDto = new ArrayList<>();
		for (Order Order : entities) {
			OrderDto dto = new OrderDto();
			dto.setId(Order.getId());
			dto.setUserId(Order.getUser().getId());
			dto.setBookId(Order.getBook().getId());
			dto.setNotificationId(Order.getNotification().getId());
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
			dto.setNotificationId(entity.getNotification().getId());
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
		entity.getNotification().setId(dto.getNotificationId());
		entity.setOrderDate(dto.getOrderDate());
		entity.setDueDate(dto.getDueDate());
		entity.setFinished(dto.isFinished());
		entity.setProlongation(dto.isProlongation());
		return entity;
	}
}
