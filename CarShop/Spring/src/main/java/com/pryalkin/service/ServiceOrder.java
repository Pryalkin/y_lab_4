package com.pryalkin.service;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.OrderResponseDTO;

import java.util.List;

public interface ServiceOrder {

    HttpResponse<MessageResponse> createOrder(OrderRequestDTO order, String token);

    HttpResponse<List<OrderResponseDTO>>  getAllOrders(String token) ;

    HttpResponse<List<OrderResponseDTO>> findOrderByClient(String client, String token) ;

}
