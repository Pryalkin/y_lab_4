package com.pryalkin.controller;

import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.exception.ExceptionHandling;
import com.pryalkin.exception.model.CarDontExistException;
import com.pryalkin.exception.model.CarsDontExistException;
import com.pryalkin.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/car")
public class CarController extends ExceptionHandling {

    private final CarService serviceCar;

    public CarController(CarService serviceCar) {
        this.serviceCar = serviceCar;
    }

    @PostMapping("/add")
    public ResponseEntity<CarResponseDTO> add(@RequestBody NewCarRequestDTO newCarRequestDTO) {
        return new ResponseEntity<>(serviceCar.addCar(newCarRequestDTO), OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponseDTO>> all() throws CarsDontExistException {
        return new ResponseEntity<>(serviceCar.getCars(), OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CarResponseDTO> delete(@RequestParam Long carId) throws CarDontExistException {
        serviceCar.deleteCar(carId);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<CarResponseDTO>> find(@RequestParam String brand,
                                                     @RequestParam String model) throws CarsDontExistException {
        return new ResponseEntity<>(serviceCar.findCarBrandModel(brand, model), OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CarResponseDTO> update(@RequestBody NewCarRequestDTO newCarRequestDTO) {
        return new ResponseEntity<>(serviceCar.addCar(newCarRequestDTO), OK);
    }

}
