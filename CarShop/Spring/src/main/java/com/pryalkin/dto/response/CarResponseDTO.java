package com.pryalkin.dto.response;

import lombok.Data;

@Data
public class CarResponseDTO {

    private Long id;
    private String brand;
    private String model;
    private String yearOfIssue;
    private String price;
    private String state;
    private String inStock;

}
