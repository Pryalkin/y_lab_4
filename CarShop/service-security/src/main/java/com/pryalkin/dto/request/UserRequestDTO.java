package com.pryalkin.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserRequestDTO {

    private String email;
    private String password;
    private String name;
    private String surname;

}
