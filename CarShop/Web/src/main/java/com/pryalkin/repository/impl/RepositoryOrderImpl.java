package com.pryalkin.repository.impl;

import com.pryalkin.annotation.Repository;
import com.pryalkin.model.Order;
import com.pryalkin.repository.RepositoryOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RepositoryOrderImpl implements RepositoryOrder {

    private Long idOrder = 0L;
    private Map<Long, Order> orders = new HashMap<>();

    @Override
    public Order saveOrder(Order order) {
        if(order.getId() == null){
            idOrder++;
            order.setId(idOrder.toString());
            orders.put(idOrder, order);
        } else {
            orders.put(Long.parseLong(order.getId()), order);
        }
        return order;
    }

    @Override
    public Collection<Order> findOrders() {
        return orders.values();
    }

    @Override
    public List<Order> findOrderByClient(String id) {
        return orders.values().stream().filter(order -> order.getIdUser().equals(id)).collect(Collectors.toList());
    }

}
