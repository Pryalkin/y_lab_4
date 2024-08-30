package com.pryalkin.mapper.impl;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.mapper.CarMapper;
import com.pryalkin.mapper.OrderMapper;
import com.pryalkin.mapper.UserShopMapper;
import com.pryalkin.model.Car;
import com.pryalkin.model.Order;
import com.pryalkin.model.UserShop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderShopImpl implements OrderMapper {

    private final UserShopMapper userShopMapper;
    private final CarMapper carMapper;


    @Override
    public Order orderRequestDtoToOrder(OrderRequestDTO orderRequestDTO) {
        return null;
    }

    @Override
    public OrderResponseDTO orderToOrderResponseDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setStatus(order.getStatus());
        UserResponseDTO userResponseDTO = userShopMapper.userShopToUserShopResponseDTO(order.getUserShop());
        orderResponseDTO.setUserResponseDTO(userResponseDTO);
        CarResponseDTO carResponseDTO = carMapper.carToCarResponseDTO(order.getCar());
        orderResponseDTO.setCarResponseDTO(carResponseDTO);
        return orderResponseDTO;
    }
}
