package com.pryalkin.dto.request;

public class CarRequestDTO {

    private Integer id;
    private String brand;
    private String model;
    private String yearOfIssue;
    private String price;
    private String state;
    private String inStock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYearOfIssue(String yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYearOfIssue() {
        return yearOfIssue;
    }

    public String getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public String getInStock() {
        return inStock;
    }

}
