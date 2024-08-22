package com.pryalkin.repository.impl;

import com.pryalkin.annotation.Repository;
import com.pryalkin.model.Car;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;
import com.pryalkin.repository.RepositoryOrder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RepositoryOrderImpl implements RepositoryOrder {

    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = RepositoryUserImpl.class
                .getClassLoader().getResourceAsStream("app.properties")){
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order saveOrder(Order order) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO orders (status, carId, userId) values (?, ?, ?)")){
            ps.setString(1, order.getStatus());
            ps.setInt(2, order.getCar().getId());
            ps.setInt(3, order.getUser().getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setStatus(rs.getString(2));

                Integer userId = rs.getInt(3);
                PreparedStatement psUser = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
                psUser.setInt(1, userId);
                ResultSet rsUser = ps.executeQuery();
                User user = new User();
                if (rsUser.next()){
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setSurname(rs.getString(3));
                    user.setUsername(rs.getString(4));
                    user.setPassword(rs.getString(5));
                    user.setRole(rs.getString(6));
                }
                order.setUser(user);

                Integer carId = rs.getInt(4);
                PreparedStatement psCar = conn.prepareStatement("SELECT * FROM cars WHERE id = ?");
                psCar.setInt(1, carId);
                ResultSet rsCar = ps.executeQuery();
                Car car = new Car();
                if (rsCar.next()){
                    car.setId(rs.getInt(1));
                    car.setBrand(rs.getString(2));
                    car.setModel(rs.getString(3));
                    car.setYearOfIssue(rs.getString(4));
                    car.setPrice(rs.getString(5));
                    car.setState(rs.getString(6));
                    car.setInStock(rs.getString(7));
                }
                order.setCar(car);

                orders.add(order);
            }
            conn.commit();
            conn.close();
            return orders;
        } catch (SQLException e) {
            // Обработка ошибки
            try {
                // Отмена транзакции при ошибке
                conn.rollback();
            } catch (SQLException ex) {
                // Обработка ошибки при откате
                System.err.println("Ошибка при откате транзакции: " + ex.getMessage());
            }
            System.err.println("Ошибка в транзакции: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> findOrderByUserIdRole(String role) {
        List<Order> orders = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders o INNER JOIN users u ON o.userId = u.id WHERE u.role = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setStatus(rs.getString(2));
                Integer userId = rs.getInt(3);
                PreparedStatement psUser = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
                psUser.setInt(1, userId);
                ResultSet rsUser = ps.executeQuery();
                User user = new User();
                if (rsUser.next()){
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setSurname(rs.getString(3));
                    user.setUsername(rs.getString(4));
                    user.setPassword(rs.getString(5));
                    user.setRole(rs.getString(6));
                }
                order.setUser(user);

                Integer carId = rs.getInt(4);
                PreparedStatement psCar = conn.prepareStatement("SELECT * FROM cars WHERE id = ?");
                psCar.setInt(1, carId);
                ResultSet rsCar = ps.executeQuery();
                Car car = new Car();
                if (rsCar.next()){
                    car.setId(rs.getInt(1));
                    car.setBrand(rs.getString(2));
                    car.setModel(rs.getString(3));
                    car.setYearOfIssue(rs.getString(4));
                    car.setPrice(rs.getString(5));
                    car.setState(rs.getString(6));
                    car.setInStock(rs.getString(7));
                }
                order.setCar(car);
                orders.add(order);
            }
            conn.commit();
            conn.close();
            return orders;
        } catch (SQLException e) {
            // Обработка ошибки
            try {
                // Отмена транзакции при ошибке
                conn.rollback();
            } catch (SQLException ex) {
                // Обработка ошибки при откате
                System.err.println("Ошибка при откате транзакции: " + ex.getMessage());
            }
            System.err.println("Ошибка в транзакции: " + e.getMessage());
        }
        return orders;
    }

}
