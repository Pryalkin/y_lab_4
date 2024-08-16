package com.pryalkin.service;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ServiceOrder extends Service{

    OrderResponseDTO createOrder(OrderRequestDTO order);

    Collection<Order> getOrders();

    Map<User, List<Order>> findOrderClient(Order order);

}
