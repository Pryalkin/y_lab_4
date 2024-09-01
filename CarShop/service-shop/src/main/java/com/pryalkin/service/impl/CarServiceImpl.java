package com.pryalkin.service.impl;

import com.pryalkin.dao.CarDao;
import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.enumeration.InStock;
import com.pryalkin.enumeration.State;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.CarsDontExistException;
import com.pryalkin.mapper.CarMapper;
import com.pryalkin.model.Car;
import com.pryalkin.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;
    private final CarMapper carMapper;

    public CarServiceImpl(CarDao carDao,
                          CarMapper carMapper) {
        this.carDao= carDao;
        this.carMapper = carMapper;
    }

    @Override
    public CarResponseDTO addCar(NewCarRequestDTO newCar) {
        Car car = carMapper.newCarRequestDtoToCar(newCar);
        car.setState(State.NEW.name());
        car.setInStock(InStock.TRUE.name());
        carDao.save(car);
        return carMapper.carToCarResponseDTO(car);
    }

    @Override
    public List<CarResponseDTO> getCars() throws CarsDontExistException {
        return carDao.findByInStock("TRUE")
                .orElseThrow(() -> new CarsDontExistException("Машин не существуют.")).stream()
                .map(carMapper::carToCarResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) throws CarDontExistException {
        Car car = getCarById(id);
        carDao.delete(car);
    }

    @Override
    public Car getCarById(Long id) throws CarDontExistException {
        return carDao.findById(id)
                .orElseThrow(() -> new CarDontExistException("Машины не существует."));
    }

    @Override
    public CarResponseDTO updateCar(CarRequestDTO updateCar) throws CarDontExistException {
        Car car = getCarById(updateCar.getId());
        if(updateCar.getBrand() != null) car.setBrand(updateCar.getBrand());
        if(updateCar.getModel() != null) car.setModel(updateCar.getModel());
        if(updateCar.getYearOfIssue() != null) car.setYearOfIssue(updateCar.getYearOfIssue());
        if(updateCar.getPrice() != null) car.setPrice(updateCar.getPrice());
        if(updateCar.getState() != null) car.setState(updateCar.getState());
        if(updateCar.getInStock() != null) car.setInStock(updateCar.getInStock());
        carDao.update(car);
        return carMapper.carToCarResponseDTO(car);
    }

    @Override
    public List<CarResponseDTO> findCarBrandModel(String brand, String model) throws CarsDontExistException {
        return carDao.findByBrandAndModel(brand, model)
                .orElseThrow(() -> new CarsDontExistException("Машин не существуют."))
                .stream().map(carMapper::carToCarResponseDTO).toList();
    }

}
