package by.runa.lib.api.dao;

import java.util.List;

import by.runa.lib.entities.Order;

public interface IOrderDao extends IAGenericDao<Order> {

    List<Order> getAllOrdersByUserId(Long id);

}
