package com.pryalkin.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String role;

}


