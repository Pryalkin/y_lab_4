package com.pryalkin.dto.response;

public class OrderResponseDTO {

    private String status;
    private UserResponseDTO userResponseDTO;
    private CarResponseDTO carResponseDTO;

    public String getStatus() {
        return status;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }

    public CarResponseDTO getCarResponseDTO() {
        return carResponseDTO;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }

    public void setCarResponseDTO(CarResponseDTO carResponseDTO) {
        this.carResponseDTO = carResponseDTO;
    }
}
