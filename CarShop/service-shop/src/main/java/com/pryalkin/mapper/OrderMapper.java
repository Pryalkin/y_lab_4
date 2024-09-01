package com.pryalkin.mapper;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface OrderMapper {

    Order orderRequestDtoToOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO orderToOrderResponseDTO(Order order);

}
