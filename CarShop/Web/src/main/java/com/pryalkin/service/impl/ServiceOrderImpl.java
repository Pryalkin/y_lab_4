//package com.pryalkin.service.impl;
//
//import com.pryalkin.annotation.Service;
//import com.pryalkin.dto.request.OrderRequestDTO;
//import com.pryalkin.dto.response.OrderResponseDTO;
//import com.pryalkin.emun.Action;
//import com.pryalkin.emun.InStock;
//import com.pryalkin.emun.Status;
//import com.pryalkin.mapper.OrderMapper;
//import com.pryalkin.mapper.impl.OrderMapperImpl;
//import com.pryalkin.model.Car;
//import com.pryalkin.model.LoggingUsersOrder;
//import com.pryalkin.model.Order;
//import com.pryalkin.model.User;
//import com.pryalkin.repository.RepositoryCar;
//import com.pryalkin.repository.RepositoryOrder;
//import com.pryalkin.repository.RepositoryUser;
//import com.pryalkin.service.ServiceCar;
//import com.pryalkin.service.ServiceOrder;
//
//import java.util.*;
//
//@Service
//public class ServiceOrderImpl implements ServiceOrder {
//
//    private final RepositoryOrder repositoryOrder;
//    private final RepositoryUser repositoryUser;
//    private final RepositoryCar repositoryCar;
//    private final ServiceCar serviceCar;
//
//    public ServiceOrderImpl(RepositoryOrder repositoryOrder,
//                            RepositoryUser repositoryUser,
//                            RepositoryCar repositoryCar,
//                            ServiceCar serviceCar) {
//        this.repositoryOrder = repositoryOrder;
//        this.repositoryUser = repositoryUser;
//        this.repositoryCar = repositoryCar;
//        this.serviceCar = serviceCar;
//    }

//    @Override
//    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
//        User user = repositoryUser.findUser(orderRequestDTO.getUserId());
//        Car car = repositoryCar.findCar(orderRequestDTO.getCarId());
//        if (user == null && car == null) {
//            OrderMapper orderMapper = new OrderMapperImpl();
//            Order order = orderMapper.orderRequestDtoToOrder(orderRequestDTO);
//            order.setStatus(Status.FOR_SALE.name());
//            repositoryOrder.saveOrder(order);
//        }
//    }


//        if(repositoryUser.findUser(Integer.parseInt(order.getIdUser())) != null){
//            LoggingUsersOrder loggingUsersOrder = new LoggingUsersOrder();
//            loggingUsersOrder.setUserId(order.getIdUser());
//            loggingUsersOrder.setAction(Action.CREATE.name());
//            loggingUsersOrder.setDate(new Date().toString());
////            repositoryOrder.insertLoggingUsersOrder(loggingUsersOrder);
//            Car car = repositoryCar.findCar(Integer.parseInt(order.getIdCar()));
//            if(car != null){
//                if (repositoryCar.findCarByIdAndInStock(car.getId(), InStock.TRUE.name()) != null){
//                    Car updateCar = new Car();
//                    updateCar.setId(car.getId());
//                    updateCar.setInStock(InStock.FALSE.name());
//                    serviceCar.updateCar(updateCar);
//                    return repositoryOrder.saveOrder(order);
//                }
//            }
//        }
//        return null;
//    }

//    @Override
//    public Collection<Order> getOrders() {
//        return repositoryOrder.findOrders();
//    }
//
//    @Override
//    public Map<User, List<Order>> findOrderClient(Order order) {
//        User user = repositoryUser.findUser(Integer.parseInt(order.getIdUser()));
//        LoggingUsersOrder loggingUsersOrder = new LoggingUsersOrder();
//        loggingUsersOrder.setUserId(order.getIdUser());
//        loggingUsersOrder.setAction(Action.FIND_CLIENT.name());
//        loggingUsersOrder.setDate(new Date().toString());
////        repositoryOrder.insertLoggingUsersOrder(loggingUsersOrder);
//        List<Order> orders = repositoryOrder.findOrderByClient(user.getId());
//        return new HashMap<User, List<Order>>(){{
//            put(user, orders);
//        }};
//    }

//    }
//}
