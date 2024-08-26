package com.pryalkin.controller;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.service.ServiceOrder;
import jakarta.servlet.http.HttpServletRequest;
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
    public HttpResponse<MessageResponse> create(@RequestBody OrderRequestDTO orderRequestDTO,
                                                HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        return serviceOrder.createOrder(orderRequestDTO, token.substring("Bearer ".length()));
    }

    @GetMapping("/all")
    public HttpResponse<List<OrderResponseDTO>> all(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        return serviceOrder.getAllOrders(token.substring("Bearer ".length()));
    }

    @GetMapping("/find")
    public HttpResponse<List<OrderResponseDTO>> find(@RequestParam(value = "client") String client,
                                                     HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        return serviceOrder.findOrderByClient(client, token.substring("Bearer ".length()));
    }


}
