package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.entities.Order;

@Repository
public class OrderDao extends AGenericDao<Order> {

	public OrderDao(Class<Order> clazz) {
		super(Order.class);
	}
}
