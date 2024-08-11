package com.pryalkin.service;

import com.pryalkin.model.*;

import java.util.List;

public interface ServiceCar extends Service{

    Car addCar(Car car);

    List<Car> getCars();

    Car deleteCar(String idCar);

    Car updateCar(Car car);

    List<Car> findCarBrandModel(Car car);

}
