package com.pryalkin.service.impl;

import com.pryalkin.annotation.Service;
import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.emun.InStock;
import com.pryalkin.emun.State;
import com.pryalkin.mapper.CarMapper;
import com.pryalkin.mapper.impl.CarMapperImpl;
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
    public CarResponseDTO addCar(NewCarRequestDTO newCar) {
        CarMapper carMapper = new CarMapperImpl();
        Car car = carMapper.newCarRequestDtoToCar(newCar);
        car.setState(State.NEW.name());
        car.setInStock(InStock.TRUE.name());
        car = repositoryCar.saveCar(car);
        return carMapper.carToCarResponseDTO(car);
    }

    @Override
    public List<CarResponseDTO> getCars() {
        return repositoryCar.findCars().stream().filter(car -> car.getInStock().equals("TRUE"))
                .map(new CarMapperImpl()::carToCarResponseDTO).collect(Collectors.toList());
    }

    @Override
    public String deleteCar(String idCar) {
        repositoryCar.deleteCar(idCar);
        return "Машина успешна удалена!";
    }

    @Override
    public CarResponseDTO updateCar(CarRequestDTO updateCar) {
        Car car = repositoryCar.findCar(updateCar.getId());
        if(updateCar.getBrand() != null) car.setBrand(updateCar.getBrand());
        if(updateCar.getModel() != null) car.setModel(updateCar.getModel());
        if(updateCar.getYearOfIssue() != null) car.setYearOfIssue(updateCar.getYearOfIssue());
        if(updateCar.getPrice() != null) car.setPrice(updateCar.getPrice());
        if(updateCar.getState() != null) car.setState(updateCar.getState());
        if(updateCar.getInStock() != null) car.setInStock(updateCar.getInStock());
        return new CarMapperImpl().carToCarResponseDTO(repositoryCar.saveCar(car));
    }

    @Override
    public List<CarResponseDTO> findCarBrandModel(String brand, String model) {
        return repositoryCar.findCarByBrandAndModel(brand, model).stream().map(new CarMapperImpl()::carToCarResponseDTO).toList();
    }

}
