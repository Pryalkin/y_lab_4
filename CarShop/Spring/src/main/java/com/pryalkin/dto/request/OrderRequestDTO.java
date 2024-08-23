package com.pryalkin.dto.request;

public class OrderRequestDTO {

    private Long carId;
    private Long userId;


    public Long getCarId() {
        return carId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
