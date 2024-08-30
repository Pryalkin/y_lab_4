package com.pryalkin.dto.request;

import lombok.Data;

@Data
public class CarRequestDTO {

    private Long id;
    private String brand;
    private String model;
    private String yearOfIssue;
    private Double price;
    private String state;
    private String inStock;

}
