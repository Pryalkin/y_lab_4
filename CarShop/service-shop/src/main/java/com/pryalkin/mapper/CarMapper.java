package com.pryalkin.mapper;

import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target="brand", source="newCar.brand")
    @Mapping(target="model", source="newCar.model")
    @Mapping(target="yearOfIssue", source="newCar.yearOfIssue")
    @Mapping(target="price", source="newCar.price")
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "inStock", ignore = true)
    Car newCarRequestDtoToCar(NewCarRequestDTO newCar);

    @Mapping(target = "id", source="car.id")
    @Mapping(target="brand", source="car.brand")
    @Mapping(target="model", source="car.model")
    @Mapping(target="yearOfIssue", source="car.yearOfIssue")
    @Mapping(target="price", source="car.price")
    @Mapping(target = "state", source="car.state")
    @Mapping(target = "inStock", source="car.inStock")
    CarResponseDTO carToCarResponseDTO(Car car);

    @Mapping(target = "id", source="carRequestDTO.id")
    @Mapping(target="brand", source="carRequestDTO.brand")
    @Mapping(target="model", source="carRequestDTO.model")
    @Mapping(target="yearOfIssue", source="carRequestDTO.yearOfIssue")
    @Mapping(target="price", source="carRequestDTO.price")
    @Mapping(target = "state", source="carRequestDTO.state")
    @Mapping(target = "inStock", source="carRequestDTO.inStock")
    CarResponseDTO carRequestToCar(CarRequestDTO carRequestDTO);

}
