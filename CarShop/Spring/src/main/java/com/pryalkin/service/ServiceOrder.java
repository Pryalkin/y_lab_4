package com.pryalkin.service;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.OrderResponseDTO;

import java.util.List;

public interface ServiceOrder {

    HttpResponse<MessageResponse> createOrder(OrderRequestDTO order);

    HttpResponse<List<OrderResponseDTO>>  getAllOrders() ;

    HttpResponse<List<OrderResponseDTO>> findOrderByClient(String client) ;

}
