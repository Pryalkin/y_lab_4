package com.pryalkin.service;

import com.pryalkin.annotation.Token;
import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.OrdersDontExistException;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO order, @Token String token) throws CarDontExistException;

    List<OrderResponseDTO>  getAllOrders(@Token String token) throws OrdersDontExistException;

    List<OrderResponseDTO> findOrderByClient(String client, @Token String token) throws OrdersDontExistException;

}
