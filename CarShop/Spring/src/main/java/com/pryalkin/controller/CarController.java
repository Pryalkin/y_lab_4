package com.pryalkin.controller;

import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.service.ServiceCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private final ServiceCar serviceCar;

    @Autowired
    public CarController(ServiceCar serviceCar) {
        this.serviceCar = serviceCar;
    }

    @PostMapping("/add")
    public HttpResponse<CarResponseDTO> add(@RequestBody NewCarRequestDTO newCarRequestDTO) {
        return serviceCar.addCar(newCarRequestDTO);
    }

    @GetMapping("/all")
    public HttpResponse<List<CarResponseDTO>> all(@RequestBody NewCarRequestDTO newCarRequestDTO) {
        return serviceCar.getCars();
    }

    @DeleteMapping("/delete")
    public HttpResponse<MessageResponse> delete(@RequestParam Long carId) {
        return serviceCar.deleteCar(carId);
    }

    @GetMapping("/find")
    public HttpResponse<List<CarResponseDTO>> find(@RequestParam String brand,
                                                   @RequestParam String model) {
        return serviceCar.findCarBrandModel(brand, model);
    }

    @PostMapping("/update")
    public HttpResponse<CarResponseDTO> update(@RequestBody NewCarRequestDTO newCarRequestDTO) {
        return serviceCar.addCar(newCarRequestDTO);
    }
}
