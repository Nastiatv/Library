package com.runa.lib.dao;

import com.runa.lib.entities.Notification;

public class NotificationDao extends AGenericDao<Notification> {

	public NotificationDao(Class<Notification> clazz) {
		super(Notification.class);
		}

}
