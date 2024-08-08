package com.pryalkin.repository.impl;

import com.pryalkin.annotation.Repository;
import com.pryalkin.model.Car;
import com.pryalkin.repository.RepositoryCar;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RepositoryCarImpl implements RepositoryCar {

    private Long idCar = 0L;
    private Map<Long, Car> cars = new HashMap<>();

    @Override
    public Car saveCar(Car car) {
        if(car.getId() == null){
            idCar++;
            car.setId(idCar.toString());
            cars.put(idCar, car);
        } else {
            cars.put(Long.parseLong(car.getId()), car);
        }
        return car;
    }

    @Override
    public Collection<Car> findCars() {
        return cars.values();
    }

    @Override
    public Car deleteCar(String idCar) {
        return cars.remove(Long.parseLong(idCar));
    }

    @Override
    public Car findCar(Long id) {
        return cars.get(id);
    }

    @Override
    public Car findCarByIdAndInStock(String id, String name) {
        Car car = cars.get(Long.parseLong(id));
        if(car != null)
            if(car.getInStock().equals(name))
                return car;
        return null;
    }

    @Override
    public List<Car> findCarByBrandAndModel(String brand, String model) {
        return cars.values().stream().filter(car -> car.getBrand().equals(brand)
                && car.getModel().equals(model)).collect(Collectors.toList());
    }
}
