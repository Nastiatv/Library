package by.runa.lib.dao;

import org.springframework.stereotype.Repository;

import by.runa.lib.api.dao.IOrderDao;
import by.runa.lib.entities.Order;

@Repository
public class OrderDao extends AGenericDao<Order> implements IOrderDao {

	public OrderDao() {
		super(Order.class);
	}
}
