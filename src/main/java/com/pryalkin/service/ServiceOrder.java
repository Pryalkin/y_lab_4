package com.pryalkin.service;

import com.pryalkin.model.Order;
import com.pryalkin.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ServiceOrder extends Service{

    Order createOrder(Order order);

    Collection<Order> getOrders();

    Map<User, List<Order>> findOrderClient(Order order);

}
