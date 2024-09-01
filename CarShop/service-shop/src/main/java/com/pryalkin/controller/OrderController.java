package com.pryalkin.controller;

import com.pryalkin.annotation.Token;
import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.exception.ExceptionHandling;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.OrdersDontExistException;
import com.pryalkin.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends ExceptionHandling {

    private final OrderService serviceOrder;

    @Autowired
    public OrderController(OrderService serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO orderRequestDTO,
                                                   HttpServletRequest http) throws CarDontExistException {
        String token = http.getHeader("Authorization").substring("Bearer ".length());
        return new ResponseEntity<>(serviceOrder.createOrder(orderRequestDTO, token), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> all(HttpServletRequest http) throws OrdersDontExistException {
        String token = http.getHeader("Authorization").substring("Bearer ".length());
        return new ResponseEntity<>(serviceOrder.getAllOrders(token), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<OrderResponseDTO>> find(@RequestParam String client,
                                                       HttpServletRequest http) throws OrdersDontExistException {
        String token = http.getHeader("Authorization").substring("Bearer ".length());
        return new ResponseEntity<>(serviceOrder.findOrderByClient(client, token), HttpStatus.OK);
    }


}
