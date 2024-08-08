package com.pryalkin.service;

import com.pryalkin.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ServiceOrder extends Service{

    Order createOrder(Order order);

    Collection<Order> getOrders();

    Map<User, List<Order>> findOrderClient(Order order);

}
