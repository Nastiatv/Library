package com.runa.lib.api.converters;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.api.dto.NotificationDto;
import com.runa.lib.entities.Notification;

public class NotificationConverter {

	public static List<NotificationDto> convertList(List<Notification> entities) {
		List<NotificationDto> listDto = new ArrayList<>();
		for (Notification Notification : entities) {
			NotificationDto dto = new NotificationDto();
			dto.setId(Notification.getId());
			dto.setNotificationMessage(Notification.getNotificationMessage());
			listDto.add(dto);
		}
		return listDto;
	}

	public static NotificationDto entityToDto(Notification entity) {
		NotificationDto dto = new NotificationDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setNotificationMessage(entity.getNotificationMessage());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Notification dtoToEntity(NotificationDto dto) {
		Notification entity = new Notification();
		entity.setId(dto.getId());
		entity.setNotificationMessage(dto.getNotificationMessage());
		return entity;
	}
}
