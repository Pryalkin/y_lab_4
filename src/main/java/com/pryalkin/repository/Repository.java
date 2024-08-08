package com.pryalkin.repository;

import com.pryalkin.model.Car;
import com.pryalkin.model.LoggingUsersOrder;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;

import java.util.Collection;
import java.util.List;

public interface Repository {

    void sayHello();

    User saveUser(User user);
    Collection<User> findUsers();

    Car saveCar(Car car);

    User findUserByUsernameAndPassword(String username, String password);

    Collection<Car> findCars();

    Car deleteCar(String idCar);

    Car findCar(Long id);

    User findUser(Long id);

    Car findCarByIdAndInStock(String id, String name);

    Order saveOrder(Order order);

    Collection<Order> findOrders();

    List<Car> findCarByBrandAndModel(String brand, String model);

    List<Order> findOrderByClient(String id);

    LoggingUsersOrder insertLoggingUsersOrder(LoggingUsersOrder loggingUsersOrder);

    List<LoggingUsersOrder> findLogging(String id);
}
