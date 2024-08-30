package com.pryalkin.model;

import lombok.*;

@Data
public class UserShop {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String role;
    private String[] authorities;

}
