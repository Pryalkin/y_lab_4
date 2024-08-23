package com.pryalkin.service.impl;

import com.pryalkin.dao.CarDao;
import com.pryalkin.dao.OrderDao;
import com.pryalkin.dao.UserDao;
import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.emun.Status;
import com.pryalkin.emun.StatusCode;
import com.pryalkin.mapper.OrderMapper;
import com.pryalkin.mapper.impl.OrderMapperImpl;
import com.pryalkin.model.Car;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceCar;
import com.pryalkin.service.ServiceLoggingUser;
import com.pryalkin.service.ServiceOrder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceOrderImpl implements ServiceOrder {

    private final UserDao userDao;
    private final CarDao carDao;
    private final OrderDao orderDao;
    private final ServiceCar serviceCar;


    public ServiceOrderImpl(UserDao userDao,
                            CarDao carDao,
                            OrderDao orderDao,
                            ServiceCar serviceCar) {
        this.userDao = userDao;
        this.carDao = carDao;
        this.orderDao = orderDao;
        this.serviceCar = serviceCar;
    }

    @Override
    public HttpResponse<MessageResponse> createOrder(OrderRequestDTO orderRequestDTO) {
        User user = userDao.findById(orderRequestDTO.getUserId());
        Car car = carDao.findById(orderRequestDTO.getCarId());
        if (user != null && car != null) {
            OrderMapper orderMapper = new OrderMapperImpl();
            Order order = orderMapper.orderRequestDtoToOrder(orderRequestDTO);
            order.setStatus(Status.FOR_SALE.name());
            order.setCar(car);
            order.setUser(user);
            orderDao.save(order);
        }
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Заказ принят от " + user.getName() + " " +  user.getSurname() + "!");
        Map<String, MessageResponse> response = new HashMap<>();
        response.put(MessageResponse.class.getSimpleName(), messageResponse);
        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Принятие заказа!", response);
    }

    @Override
    public HttpResponse<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderDao.findAll().stream().map(new OrderMapperImpl()::orderToOrderResponseDTO).toList();
        Map<String, List<OrderResponseDTO>> response = new HashMap<>();
        response.put(CarResponseDTO.class.getSimpleName(), orders);
        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Список всех покупок!", response);
    }

    @Override
    public HttpResponse<List<OrderResponseDTO>> findOrderByClient(String client) {
        List<OrderResponseDTO> orders = orderDao.findAll().stream().map(new OrderMapperImpl()::orderToOrderResponseDTO).toList();
        Map<String, List<OrderResponseDTO>> response = new HashMap<>();
        response.put(OrderResponseDTO.class.getSimpleName(), orders);
        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Список всех покупок клиентов!", response);
    }
}
