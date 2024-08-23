//package com.pryalkin.service.impl;
//
//import com.pryalkin.dto.request.CarRequestDTO;
//import com.pryalkin.dto.request.NewCarRequestDTO;
//import com.pryalkin.dto.response.CarResponseDTO;
//import com.pryalkin.dto.response.HttpResponse;
//import com.pryalkin.dto.response.MessageResponse;
//import com.pryalkin.emun.InStock;
//import com.pryalkin.emun.State;
//import com.pryalkin.emun.StatusCode;
//import com.pryalkin.mapper.CarMapper;
//import com.pryalkin.mapper.impl.CarMapperImpl;
//import com.pryalkin.model.Car;
//import com.pryalkin.repository.RepositoryCar;
//import com.pryalkin.service.ServiceCar;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class ServiceCarImpl implements ServiceCar {
//
//    private final RepositoryCar repositoryCar;
//
//    public ServiceCarImpl(RepositoryCar repositoryCar) {
//        this.repositoryCar= repositoryCar;
//    }
//
//    @Override
//    public HttpResponse<CarResponseDTO> addCar(NewCarRequestDTO newCar) {
//        CarMapper carMapper = new CarMapperImpl();
//        Car car = carMapper.newCarRequestDtoToCar(newCar);
//        car.setState(State.NEW.name());
//        car.setInStock(InStock.TRUE.name());
//        car = repositoryCar.saveCar(car);
//        CarResponseDTO carResponseDTO = carMapper.carToCarResponseDTO(car);
//        Map<String, CarResponseDTO> response = new HashMap<>();
//        response.put(CarResponseDTO.class.getSimpleName(), carResponseDTO);
//        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
//                "Машина успешна добавлена!", response);
//    }
//
//    @Override
//    public HttpResponse<List<CarResponseDTO>> getCars() {
//        List<CarResponseDTO> carResponseDTOs = repositoryCar.findCars().stream().filter(car -> car.getInStock().equals("TRUE"))
//                .map(new CarMapperImpl()::carToCarResponseDTO).collect(Collectors.toList());
//        Map<String, List<CarResponseDTO>> response = new HashMap<>();
//        response.put(CarResponseDTO.class.getSimpleName(), carResponseDTOs);
//        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
//                "Список всех продающихся машин!", response);
//    }
//
//    @Override
//    public HttpResponse<MessageResponse> deleteCar(String idCar) {
//        repositoryCar.deleteCar(idCar);
//        MessageResponse messageResponse = new MessageResponse();
//        messageResponse.setMessage("Машина успешна удалена!");
//        Map<String, MessageResponse> response = new HashMap<>();
//        response.put(MessageResponse.class.getSimpleName(), messageResponse);
//        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
//                "Удаление машины!", response);
//    }
//
//    @Override
//    public HttpResponse<CarResponseDTO> updateCar(CarRequestDTO updateCar) {
//        Car car = repositoryCar.findCar(updateCar.getId());
//        if(updateCar.getBrand() != null) car.setBrand(updateCar.getBrand());
//        if(updateCar.getModel() != null) car.setModel(updateCar.getModel());
//        if(updateCar.getYearOfIssue() != null) car.setYearOfIssue(updateCar.getYearOfIssue());
//        if(updateCar.getPrice() != null) car.setPrice(updateCar.getPrice());
//        if(updateCar.getState() != null) car.setState(updateCar.getState());
//        if(updateCar.getInStock() != null) car.setInStock(updateCar.getInStock());
//        CarResponseDTO carResponseDTO =  new CarMapperImpl().carToCarResponseDTO(repositoryCar.saveCar(car));
//        Map<String, CarResponseDTO> response = new HashMap<>();
//        response.put(CarResponseDTO.class.getSimpleName(), carResponseDTO);
//        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
//                "Машина успешна обновлена!", response);
//    }
//
//    @Override
//    public HttpResponse<List<CarResponseDTO>> findCarBrandModel(String brand, String model) {
//        List<CarResponseDTO> carResponseDTOs =  repositoryCar.findCarByBrandAndModel(brand, model).stream().map(new CarMapperImpl()::carToCarResponseDTO).toList();
//        Map<String, List<CarResponseDTO>> response = new HashMap<>();
//        response.put(CarResponseDTO.class.getSimpleName(), carResponseDTOs);
//        return new HttpResponse<>(StatusCode.OK.getCode(), StatusCode.OK.name(),
//                "Список машины по поиску бренда и модели!", response);
//
//    }
//
//}
