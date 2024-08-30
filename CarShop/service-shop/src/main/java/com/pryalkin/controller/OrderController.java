package com.pryalkin.controller;

import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.exception.ExceptionHandling;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.OrdersDontExistException;
import com.pryalkin.service.OrderService;
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
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO orderRequestDTO) throws CarDontExistException {
        return new ResponseEntity<>(serviceOrder.createOrder(orderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> all() throws OrdersDontExistException {
        return new ResponseEntity<>(serviceOrder.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<OrderResponseDTO>> find(@RequestParam String client) throws OrdersDontExistException {
        return new ResponseEntity<>(serviceOrder.findOrderByClient(client), HttpStatus.OK);
    }


}
