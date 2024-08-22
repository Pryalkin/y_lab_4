package com.pryalkin.mapper.impl;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.mapper.OrderMapper;
import com.pryalkin.mapper.UserMapper;
import com.pryalkin.model.Car;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;

public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order orderRequestDtoToOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO == null) {
            return null;
        }
        Order order = new Order();
        User user = new User();
        user.setId(orderRequestDTO.getUserId());
        order.setUser(user);
        Car car = new Car();
        car.setId(orderRequestDTO.getCarId());
        order.setCar(car);
        return order;
    }

    @Override
    public OrderResponseDTO orderToOrderResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setStatus(order.getStatus());
        User user = order.getUser();
        UserResponseDTO userResponseDTO = new UserMapperImpl().userToUserResponseDto(user);
        responseDTO.setUserResponseDTO(userResponseDTO);
        Car car = order.getCar();
        CarResponseDTO carResponseDTO = new CarMapperImpl().carToCarResponseDTO(car);
        responseDTO.setCarResponseDTO(carResponseDTO);
        return responseDTO;
    }
}
