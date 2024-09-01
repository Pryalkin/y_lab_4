package com.pryalkin.model;

import lombok.*;

@Data
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String role;
    private String[] authorities;

}
