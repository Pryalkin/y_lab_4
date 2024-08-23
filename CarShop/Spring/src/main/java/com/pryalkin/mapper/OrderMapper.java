package com.pryalkin.mapper;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.model.Order;

public interface OrderMapper {

    Order orderRequestDtoToOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO orderToOrderResponseDTO(Order order);
}
