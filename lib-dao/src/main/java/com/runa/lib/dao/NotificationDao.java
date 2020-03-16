package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.Notification;

@Repository
public class NotificationDao extends AGenericDao<Notification> {

	public NotificationDao(Class<Notification> clazz) {
		super(Notification.class);
	}
}