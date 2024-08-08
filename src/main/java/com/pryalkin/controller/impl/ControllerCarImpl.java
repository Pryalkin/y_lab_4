package com.pryalkin.controller.impl;

import com.pryalkin.annotation.Controller;
import com.pryalkin.annotation.Url;
import com.pryalkin.controller.ControllerCar;
import com.pryalkin.model.Car;
import com.pryalkin.service.ServiceCar;

@Controller(name = "/cars")
public class ControllerCarImpl implements ControllerCar {

    private final ServiceCar serviceCar;

    public ControllerCarImpl(ServiceCar serviceCar) {
        this.serviceCar = serviceCar;
    }


    @Url(name = "/add/one", method = "POST")
    public String addCar(Car car){
        return serviceCar.addCar(car).toString();
    }

    @Url(name = "/get/all", method = "GET")
    public String getCars(){
        return serviceCar.getCars().toString();
    }

    @Url(name = "/delete", method = "DELETE")
    public String deleteCar(String idCar){
        return serviceCar.deleteCar(idCar).toString();
    }

    @Url(name = "/update", method = "PUT")
    public String updateCar(Car car){
        return serviceCar.updateCar(car).toString();
    }

    @Url(name = "/find/brand/model", method = "POST")
    public String findCarBrandModel(Car car){
        return serviceCar.findCarBrandModel(car).toString();
    }

}
