package com.pryalkin.dao;

import com.pryalkin.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao extends Dao<Car, Long>{

    Optional<List<Car>> findByInStock(String aTrue);

    Optional<List<Car>> findByBrandAndModel(String brand, String model);
}
