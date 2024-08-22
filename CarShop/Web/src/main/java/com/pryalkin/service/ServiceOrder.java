package com.pryalkin.service;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ServiceOrder extends Service{

    HttpResponse<MessageResponse> createOrder(OrderRequestDTO order);

    HttpResponse<List<OrderResponseDTO>>  getAllOrders() ;

    HttpResponse<List<OrderResponseDTO>> findOrderByClient(String client) ;

}
