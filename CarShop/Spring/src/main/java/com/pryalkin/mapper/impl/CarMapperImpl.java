package com.pryalkin.mapper.impl;

import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.mapper.CarMapper;
import com.pryalkin.model.Car;

public class CarMapperImpl implements CarMapper {

    @Override
    public Car newCarRequestDtoToCar(NewCarRequestDTO newCar) {
        Car car = new Car();
        car.setBrand(newCar.getBrand());
        car.setModel(newCar.getModel());
        car.setYearOfIssue(newCar.getYearOfIssue());
        car.setPrice(newCar.getPrice());
        return car;
    }

    @Override
    public CarResponseDTO carToCarResponseDTO(Car car) {
        CarResponseDTO carResponseDTO = new CarResponseDTO();
        carResponseDTO.setId(car.getId());
        carResponseDTO.setBrand(car.getBrand());
        carResponseDTO.setModel(car.getModel());
        carResponseDTO.setYearOfIssue(car.getYearOfIssue());
        carResponseDTO.setPrice(car.getPrice());
        carResponseDTO.setState(car.getState());
        carResponseDTO.setInStock(car.getInStock());
        return carResponseDTO;
    }
}
