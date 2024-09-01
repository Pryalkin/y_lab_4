package com.pryalkin.service.impl;

import com.pryalkin.client.SecurityClient;
import com.pryalkin.dao.OrderDao;
import com.pryalkin.dao.UserShopDao;
import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.enumeration.Status;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.OrdersDontExistException;
import com.pryalkin.mapper.OrderMapper;
import com.pryalkin.mapper.UserShopMapper;
import com.pryalkin.model.Car;
import com.pryalkin.model.Order;
import com.pryalkin.model.UserShop;
import com.pryalkin.service.AuthService;
import com.pryalkin.service.CarService;
import com.pryalkin.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CarService carService;
    private final SecurityClient securityClient;
    private final AuthService authService;
    private final OrderMapper orderMapper;
    private final UserShopDao userShopDao;
    private final UserShopMapper userShopMapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, String token) throws CarDontExistException {
        UserShop userShop = userShopDao
                .findById(orderRequestDTO.getUserId())
                .orElseGet(() -> {
                    UserResponseDTO userResponseDTO = securityClient.getUserWithId(orderRequestDTO.getUserId(), authService.getToken());
                    UserShop newUserShop = userShopMapper.userResponseToUserShop(userResponseDTO);
                    userShopDao.save(newUserShop);
                    return newUserShop;
                });
        Car car = carService.getCarById(orderRequestDTO.getCarId());
        Order order = new Order();
        order.setCar(car);
        order.setUserShop(userShop);
        order.setStatus(Status.FOR_SALE.name());
        orderDao.save(order);
        return orderMapper.orderToOrderResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(String token) throws OrdersDontExistException {
        return orderDao.findAll()
                .orElseThrow(() -> new OrdersDontExistException("Заказов нет!"))
                .stream().map(orderMapper::orderToOrderResponseDTO).toList();
    }

    @Override
    public List<OrderResponseDTO> findOrderByClient(String client, String token) throws OrdersDontExistException {
        return orderDao.findByUserRole(client)
                .orElseThrow(() -> new OrdersDontExistException("Заказов от клиентов нет!"))
                .stream().map(orderMapper::orderToOrderResponseDTO).toList();
    }
}
