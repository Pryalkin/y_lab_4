package com.pryalkin.dao.impl;

import com.pryalkin.dao.CarDao;
import com.pryalkin.dao.SessionUtil;
import com.pryalkin.model.Car;
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
public class CarDaoImpl implements CarDao {

    private final SessionUtil sessionUtil;

    @Override
    public Optional<Car> findById(Long id) {
        sessionUtil.openConnection();
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
        sessionUtil.closeConnection();
        return Objects.isNull(car.getId()) ? Optional.empty() : Optional.of(car);
    }

    @Override
    public void save(Car car) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO cars (brand, model, year_of_issue, price, state, in_stock) values (?, ?, ?, ?, ?, ?)");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getYearOfIssue());
            ps.setDouble(4, car.getPrice());
            ps.setString(5, car.getState());
            ps.setString(6, car.getInStock());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(Car car) {
        sessionUtil.openConnection();
        try {
            if(findById(car.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection()
                        .prepareStatement("UPDATE cars SET brand = ?, model = ?, year_of_issue = ?,  price = ?, state = ?, in_stock = ? WHERE id = ?");
                ps.setString(1, car.getBrand());
                ps.setString(2, car.getModel());
                ps.setString(3, car.getYearOfIssue());
                ps.setDouble(4, car.getPrice());
                ps.setString(5, car.getState());
                ps.setString(6, car.getInStock());
                ps.setLong(7, car.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void delete(Car car) {
        sessionUtil.openConnection();
        try {
            if(findById(car.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection().prepareStatement("DELETE FROM cars WHERE id = ?");
                ps.setLong(1, car.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public Optional<List<Car>> findAll() {
        sessionUtil.openConnection();
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM cars");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Car car = new Car();
                car.setId(rs.getLong(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getDouble(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return cars.isEmpty() ? Optional.empty() : Optional.of(cars);
    }

    @Override
    public Optional<List<Car>> findByInStock(String inStock) {
        sessionUtil.openConnection();
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM cars WHERE in_stock = ?");
            ps.setString(1, inStock);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Car car = new Car();
                car.setId(rs.getLong(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getDouble(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return cars.isEmpty() ? Optional.empty() : Optional.of(cars);
    }

    @Override
    public Optional<List<Car>> findByBrandAndModel(String brand, String model) {
        sessionUtil.openConnection();
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM cars WHERE brand = ? AND model = ?");
            ps.setString(1, brand);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Car car = new Car();
                car.setId(rs.getLong(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYearOfIssue(rs.getString(4));
                car.setPrice(rs.getDouble(5));
                car.setState(rs.getString(6));
                car.setInStock(rs.getString(7));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return cars.isEmpty() ? Optional.empty() : Optional.of(cars);
    }
}
