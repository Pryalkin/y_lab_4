package com.pryalkin.repository.impl;

import com.pryalkin.annotation.Repository;
import com.pryalkin.model.Car;
import com.pryalkin.repository.RepositoryCar;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class RepositoryCarImpl implements RepositoryCar {

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
    public Car saveCar(Car car) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cars (brand, model, year_of_issue, price, state, in_stock) values (?, ?, ?, ?, ?, ?)")){
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getYearOfIssue());
            ps.setDouble(4, Double.parseDouble(car.getPrice()));
            ps.setString(5, car.getState());
            ps.setString(6, car.getInStock());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public List<Car> findCars() {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cars")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getString(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    @Override
    public Car deleteCar(String idCar) {
        Car car = new Car();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM cars WHERE id = ?" )){
            ps.setInt(1, Integer.parseInt(idCar));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public Car findCar(Integer id) {
        Car car = new Car();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cars WHERE id = ?" )){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getString(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public Car findCarByIdAndInStock(String id, String isStock) {
        Car car = new Car();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ? AND isStock = ?" )){
            ps.setInt(1, Integer.parseInt(id));
            ps.setBoolean(2, Boolean.parseBoolean(isStock));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getString(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public List<Car> findCarByBrandAndModel(String brand, String model) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE brand = ? AND model = ?" )){
            ps.setString(1, brand);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getString(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }
}
