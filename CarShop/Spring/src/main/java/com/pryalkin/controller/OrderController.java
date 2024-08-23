package com.pryalkin.controller;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.service.ServiceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final ServiceOrder serviceOrder;

    @Autowired
    public OrderController(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @PostMapping("/create")
    public HttpResponse<MessageResponse> create(@RequestBody OrderRequestDTO orderRequestDTO) {
        return serviceOrder.createOrder(orderRequestDTO);
    }

    @GetMapping("/all")
    public HttpResponse<List<OrderResponseDTO>> all() {
        return serviceOrder.getAllOrders();
    }

    @GetMapping("/find")
    public HttpResponse<List<OrderResponseDTO>> find(@RequestParam(value = "client") String client) {
        return serviceOrder.findOrderByClient(client);
    }


}
