package com.pryalkin.mapper;

import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.model.Car;

public interface CarMapper {

    Car newCarRequestDtoToCar(NewCarRequestDTO newCar);
    CarResponseDTO carToCarResponseDTO(Car car);
}
