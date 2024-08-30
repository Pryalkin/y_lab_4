package com.pryalkin.service;

import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.CarsDontExistException;
import com.pryalkin.model.Car;

import java.util.List;

public interface CarService {

    CarResponseDTO addCar(NewCarRequestDTO newCar);

    List<CarResponseDTO> getCars() throws CarsDontExistException;

    void deleteCar(Long idCar) throws CarDontExistException;

    CarResponseDTO updateCar(CarRequestDTO car) throws CarDontExistException;

    List<CarResponseDTO> findCarBrandModel(String brand, String model) throws CarsDontExistException;

    Car getCarById(Long id) throws CarDontExistException;

}
