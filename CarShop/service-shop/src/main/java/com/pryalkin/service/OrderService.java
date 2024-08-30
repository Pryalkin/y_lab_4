package com.pryalkin.service;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.OrdersDontExistException;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO order) throws CarDontExistException;

    List<OrderResponseDTO>  getAllOrders() throws OrdersDontExistException;

    List<OrderResponseDTO> findOrderByClient(String client) throws OrdersDontExistException;

}
