package com.pryalkin.dto.request;

public class OrderRequestDTO {

    private Integer carId;
    private Integer userId;


    public Integer getCarId() {
        return carId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
