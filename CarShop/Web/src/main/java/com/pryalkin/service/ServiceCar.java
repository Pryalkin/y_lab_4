package com.pryalkin.service;

import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.model.Car;

import java.util.List;

public interface ServiceCar extends Service{

    CarResponseDTO addCar(NewCarRequestDTO newCar);

    List<CarResponseDTO> getCars();

    String deleteCar(String idCar);

    CarResponseDTO updateCar(CarRequestDTO car);

    List<CarResponseDTO> findCarBrandModel(String brand, String model);

}
