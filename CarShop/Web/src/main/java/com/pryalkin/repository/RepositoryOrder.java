package com.pryalkin.repository;

import com.pryalkin.model.Order;

import java.util.Collection;
import java.util.List;

public interface RepositoryOrder extends Repository{

    Order saveOrder(Order order);

    List<Order> getAllOrders();

    List<Order> findOrderByUserIdRole(String role);

}
