package com.pryalkin.dto.response;

import lombok.Data;

@Data
public class OrderResponseDTO {

    private String status;
    private UserResponseDTO userResponseDTO;
    private CarResponseDTO carResponseDTO;

}
