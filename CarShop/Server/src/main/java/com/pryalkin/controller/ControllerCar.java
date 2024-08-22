package com.pryalkin.controller;

import com.pryalkin.model.Car;

public interface ControllerCar extends Controller{

    String addCar(Car car);
    String getCars();
    String deleteCar(String idCar);
    String updateCar(Car car);
    String findCarBrandModel(Car car);
}
