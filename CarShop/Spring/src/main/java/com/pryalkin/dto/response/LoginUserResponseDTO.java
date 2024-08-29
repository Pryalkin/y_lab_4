package com.pryalkin.dto.response;

import lombok.Data;

@Data
public class LoginUserResponseDTO {

    private String username;
    private String role;
    private String[] authorities;

}
