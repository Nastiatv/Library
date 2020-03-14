package com.runa.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runa.lib.api.dto.NotificationDto;
import com.runa.lib.api.service.INotificationService;

@RestController
@RequestMapping(value = "/notifications/")
public class NotificationController {

	private static final String ID = "{id}";

	@Autowired
	INotificationService notificationService;

	@GetMapping
	public List<NotificationDto> getAllNotification() {
		return notificationService.getAllNotifications();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NotificationDto addNotification(@RequestBody NotificationDto dto) {
		return notificationService.addNotification(dto);
	}

	@PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateNotification(@PathVariable Long id, @RequestBody NotificationDto dto) {
		notificationService.updateNotification(id, dto);
	}

	@GetMapping(value = ID)
	public NotificationDto getNotification(@PathVariable Long id) {
		return notificationService.getNotificationById(id);
	}

	@DeleteMapping(value = ID)
	public void deleteNotification(@PathVariable Long id) {
		notificationService.deleteNotificationById(id);
	}
}
