package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.INotificationDao;
import com.runa.lib.entities.Notification;

@Repository
public class NotificationDao extends AGenericDao<Notification> implements INotificationDao {

	public NotificationDao() {
		super(Notification.class);
	}
}