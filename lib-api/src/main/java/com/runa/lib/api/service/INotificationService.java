package com.runa.lib.api.service;

import java.util.List;

import com.runa.lib.api.dto.NotificationDto;

public interface INotificationService {

	List<NotificationDto> getAllNotifications();

	NotificationDto addNotification(NotificationDto dto);

	NotificationDto getNotificationById(Long id);

	void deleteNotificationById(Long id);

	void updateNotification(Long id, NotificationDto dto);

}
