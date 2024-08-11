package com.pryalkin.service.impl;

import com.pryalkin.model.Car;
import com.pryalkin.repository.RepositoryCar;
import com.pryalkin.service.ServiceCar;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCarImpl implements ServiceCar {

    private final RepositoryCar repositoryCar;

    public ServiceCarImpl(RepositoryCar repositoryCar) {
        this.repositoryCar= repositoryCar;
    }

    @Override
    public Car addCar(Car car) {
        return repositoryCar.saveCar(car);
    }

    @Override
    public List<Car> getCars() {
        return repositoryCar.findCars().stream().filter(car -> car.getInStock().equals("TRUE")).collect(Collectors.toList());
    }

    @Override
    public Car deleteCar(String idCar) {
        return repositoryCar.deleteCar(idCar);
    }

    @Override
    public Car updateCar(Car updateCar) {
        Car car = repositoryCar.findCar(Integer.parseInt(updateCar.getId()));
        if(updateCar.getBrand() != null) car.setBrand(updateCar.getBrand());
        if(updateCar.getModel() != null) car.setModel(updateCar.getModel());
        if(updateCar.getYearOfIssue() != null) car.setYearOfIssue(updateCar.getYearOfIssue());
        if(updateCar.getPrice() != null) car.setPrice(updateCar.getPrice());
        if(updateCar.getState() != null) car.setState(updateCar.getState());
        if(updateCar.getInStock() != null) car.setInStock(updateCar.getInStock());
        return repositoryCar.saveCar(car);
    }

    @Override
    public List<Car> findCarBrandModel(Car findCar) {
        return repositoryCar.findCarByBrandAndModel(findCar.getBrand(), findCar.getModel());
    }

}
