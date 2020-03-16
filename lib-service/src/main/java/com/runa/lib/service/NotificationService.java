package com.runa.lib.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runa.lib.api.dao.INotificationDao;
import com.runa.lib.api.dto.NotificationDto;
import com.runa.lib.api.service.INotificationService;
import com.runa.lib.entities.Notification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class NotificationService implements INotificationService {

	@Autowired
	private INotificationDao notificationDao;

	@Override
	public List<NotificationDto> getAllNotifications() {
		return NotificationDto.convertList(notificationDao.getAll());
	}

	@Override
	public NotificationDto addNotification(NotificationDto dto) {
		Notification notification = new Notification();
		notification.setAnnouncement(dto.getAnnouncement());
		return NotificationDto.entityToDto(notificationDao.create(notification));
	}

	@Override
	public NotificationDto getNotificationById(Long id) {
		return Optional.ofNullable(NotificationDto.entityToDto(notificationDao.get(id))).orElse(new NotificationDto());
	}

	@Override
	public void deleteNotificationById(Long id) {
		notificationDao.delete(notificationDao.get(id));
		log.info("Notification successfully deleted");
	}

	@Override
	public void updateNotification(Long id, NotificationDto dto) {
		Notification existingNotification = Optional.ofNullable(notificationDao.get(id)).orElse(new Notification());
		existingNotification.setAnnouncement(dto.getAnnouncement());
		notificationDao.update(existingNotification);
		log.info("Notification successfully updated");

	}
}