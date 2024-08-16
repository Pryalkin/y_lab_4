package com.pryalkin.mapper.impl;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.mapper.OrderMapper;
import com.pryalkin.model.Order;

public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order orderRequestDtoToOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO == null) {
            return null;
        }
        Order order = new Order();
        order.setIdUser(orderRequestDTO.getUserId());
        order.setIdCar(orderRequestDTO.getCarId());
        return order;
    }
}
