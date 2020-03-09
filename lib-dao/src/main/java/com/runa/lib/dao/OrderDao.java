package com.runa.lib.dao;

import com.runa.lib.entities.Order;

public class OrderDao extends AGenericDao<Order> {

	public OrderDao(Class<Order> clazz) {
		super(Order.class);
	}

}
