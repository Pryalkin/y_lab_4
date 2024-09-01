package com.pryalkin.dto.request;

import lombok.Data;

@Data
public class NewCarRequestDTO {

    private String brand;
    private String model;
    private String yearOfIssue;
    private Double price;

}
