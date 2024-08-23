package com.pryalkin.service;

import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;

import java.util.List;

public interface ServiceCar {

    HttpResponse<CarResponseDTO> addCar(NewCarRequestDTO newCar);

    HttpResponse<List<CarResponseDTO>> getCars();

    HttpResponse<MessageResponse> deleteCar(Long idCar);

    HttpResponse<CarResponseDTO> updateCar(CarRequestDTO car);

    HttpResponse<List<CarResponseDTO>> findCarBrandModel(String brand, String model);

}
