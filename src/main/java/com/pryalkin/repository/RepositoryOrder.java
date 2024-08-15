package com.pryalkin.repository;

import com.pryalkin.model.Order;

import java.util.Collection;
import java.util.List;

public interface RepositoryOrder extends Repository{

    Order saveOrder(Order order);

    Collection<Order> findOrders();

    List<Order> findOrderByClient(String id);

}
