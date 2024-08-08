package com.pryalkin.repository;

import com.pryalkin.model.Car;

import java.util.Collection;
import java.util.List;

public interface RepositoryCar extends Repository{

    public Car saveCar(Car car);
    Collection<Car> findCars();
    Car deleteCar(String idCar);
    Car findCar(Long id);
    Car findCarByIdAndInStock(String id, String name);
    List<Car> findCarByBrandAndModel(String brand, String model);
}
