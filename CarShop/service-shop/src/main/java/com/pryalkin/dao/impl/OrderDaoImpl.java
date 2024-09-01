package com.pryalkin.dao.impl;

import com.pryalkin.dao.CarDao;
import com.pryalkin.dao.OrderDao;
import com.pryalkin.dao.SessionUtil;
import com.pryalkin.dao.UserShopDao;
import com.pryalkin.model.Car;
import com.pryalkin.model.Order;
import com.pryalkin.model.UserShop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final SessionUtil sessionUtil;

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Order order) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO orders (status, user_id, car_id) values (?, ?, ?)");
            ps.setString(1, order.getStatus());
            ps.setLong(2, order.getUserShop().getId());
            ps.setLong(3, order.getCar().getId());
            ps.execute();
            sessionUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public Optional<List<Order>> findAll() {
        sessionUtil.openConnection();
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM orders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getLong(1));
                order.setStatus(rs.getString(2));
                Long userId = rs.getLong(3);
                UserShop userShop = findUserShopById(userId).orElse(new UserShop());
                order.setUserShop(userShop);
                Long carId = rs.getLong(4);
                Car car = findCarById(carId).orElse(new Car());
                order.setCar(car);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return orders.isEmpty() ? Optional.empty() : Optional.of(orders);
    }

    @Override
    public Optional<List<Order>> findByUserRole(String client) {
        sessionUtil.openConnection();
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM orders o INNER JOIN user_shop u ON o.user_id = u.id WHERE u.role = ?");
            ps.setString(1, client);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getLong(1));
                order.setStatus(rs.getString(2));
                Long userId = rs.getLong(3);
                UserShop userShop = findUserShopById(userId).orElse(new UserShop());
                order.setUserShop(userShop);
                Long carId = rs.getLong(4);
                Car car = findCarById(carId).orElse(new Car());
                order.setCar(car);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return orders.isEmpty() ? Optional.empty() : Optional.of(orders);
    }

    private Optional<UserShop> findUserShopById(Long id) {
        UserShop user = new UserShop();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM user_shop WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getLong(1));
                user.setEmail(rs.getString(2));
                user.setName(rs.getString(3));
                user.setSurname(rs.getString(4));
                user.setRole(rs.getString(5));
                String[] auths = rs.getString(6).split(",");
                user.setAuthorities(auths);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Objects.isNull(user.getId()) ? Optional.empty() : Optional.of(user);
    }

    private Optional<Car> findCarById(Long id) {
        Car car = new Car();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM cars WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                car.setId(rs.getLong(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getDouble(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Objects.isNull(car.getId()) ? Optional.empty() : Optional.of(car);
    }

}
