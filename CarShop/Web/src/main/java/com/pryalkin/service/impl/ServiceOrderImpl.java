package com.pryalkin.service.impl;

import com.pryalkin.annotation.Service;
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
import com.pryalkin.repository.RepositoryCar;
import com.pryalkin.repository.RepositoryOrder;
import com.pryalkin.repository.RepositoryUser;
import com.pryalkin.service.ServiceCar;
import com.pryalkin.service.ServiceLoggingUser;
import com.pryalkin.service.ServiceOrder;

import java.util.*;

@Service
public class ServiceOrderImpl implements ServiceOrder {

    private final RepositoryOrder repositoryOrder;
    private final RepositoryUser repositoryUser;
    private final RepositoryCar repositoryCar;
    private final ServiceCar serviceCar;
    private final ServiceLoggingUser serviceLoggingUser;

    public ServiceOrderImpl(RepositoryOrder repositoryOrder,
                            RepositoryUser repositoryUser,
                            RepositoryCar repositoryCar,
                            ServiceCar serviceCar,
                            ServiceLoggingUser serviceLoggingUser) {
        this.repositoryOrder = repositoryOrder;
        this.repositoryUser = repositoryUser;
        this.repositoryCar = repositoryCar;
        this.serviceCar = serviceCar;
        this.serviceLoggingUser = serviceLoggingUser;
    }

    @Override
    public HttpResponse<MessageResponse> createOrder(OrderRequestDTO orderRequestDTO) {
        User user = repositoryUser.findUser(orderRequestDTO.getUserId());
        Car car = repositoryCar.findCar(orderRequestDTO.getCarId());
        if (user != null && car != null) {
            OrderMapper orderMapper = new OrderMapperImpl();
            Order order = orderMapper.orderRequestDtoToOrder(orderRequestDTO);
            order.setStatus(Status.FOR_SALE.name());
            order.setCar(car);
            order.setUser(user);
            repositoryOrder.saveOrder(order);
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
        List<OrderResponseDTO> orders = repositoryOrder.getAllOrders().stream().map(new OrderMapperImpl()::orderToOrderResponseDTO).toList();
        Map<String, List<OrderResponseDTO>> response = new HashMap<>();
        response.put(CarResponseDTO.class.getSimpleName(), orders);
        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Список всех покупок!", response);
    }

    @Override
    public HttpResponse<List<OrderResponseDTO>> findOrderByClient(String client) {
        List<OrderResponseDTO> orders = repositoryOrder.getAllOrders().stream().map(new OrderMapperImpl()::orderToOrderResponseDTO).toList();
        Map<String, List<OrderResponseDTO>> response = new HashMap<>();
        response.put(CarResponseDTO.class.getSimpleName(), orders);
        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Список всех покупок клиентов!", response);
    }
}
