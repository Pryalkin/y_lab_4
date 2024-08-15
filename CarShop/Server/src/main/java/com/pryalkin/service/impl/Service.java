package com.pryalkin.service.impl;

import com.pryalkin.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Service {

    public void sayHello();

    User registration(User user);

    String getAuthorization(User user);

    Set<User> getRegistrationClient();

    Car addCar(Car car);

    User getUser(User user);

    boolean checkToken(String authorities);

    List<Car> getCars();

    Car deleteCar(String idCar);

    Car updateCar(Car car);

    Order createOrder(Order order);

    Collection<Order> getOrders();

    List<Car> findCarBrandModel(Car car);

    Map<User, List<Order>> findOrderClient(Order order);

    Set<User> getRegistrationManager();

    Map<User, List<LoggingUsersOrder>> logging(User user);

    DTO exportLogging(User user);

    String encrypt(String text, int shift);

    String decrypt(String encryptedText, int shift);
}
