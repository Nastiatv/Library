package com.runa.lib.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.runa.lib.entities.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

	private Long id;
	private String announcement;

	public static List<NotificationDto> convertList(List<Notification> entities) {
		List<NotificationDto> listDto = new ArrayList<>();
		for (Notification Notification : entities) {
			NotificationDto dto = new NotificationDto();
			dto.setId(Notification.getId());
			dto.setAnnouncement(Notification.getAnnouncement());
			listDto.add(dto);
		}
		return listDto;
	}

	public static NotificationDto entityToDto(Notification entity) {
		NotificationDto dto = new NotificationDto();
		dto.setId(entity.getId());
		if (entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setAnnouncement(entity.getAnnouncement());
		} else {
			dto.setId(null);
		}
		return dto;
	}

	public static Notification dtoToEntity(NotificationDto dto) {
		Notification entity = new Notification();
		entity.setId(dto.getId());
		entity.setAnnouncement(dto.getAnnouncement());
		return entity;
	}
}
