package com.pryalkin.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String yearOfIssue;
    private String price;
    private String state;
    private String inStock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(yearOfIssue, car.yearOfIssue) && Objects.equals(price, car.price) && Objects.equals(state, car.state) && Objects.equals(inStock, car.inStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, yearOfIssue, price, state, inStock);
    }

}
