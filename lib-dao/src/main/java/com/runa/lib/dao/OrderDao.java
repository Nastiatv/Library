package com.runa.lib.dao;

import org.springframework.stereotype.Repository;

import com.runa.lib.api.dao.IOrderDao;
import com.runa.lib.entities.Order;

@Repository
public class OrderDao extends AGenericDao<Order> implements IOrderDao {

	public OrderDao() {
		super(Order.class);
	}
}
