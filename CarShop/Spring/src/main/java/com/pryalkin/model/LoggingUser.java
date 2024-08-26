package com.pryalkin.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LoggingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String action;
    private String date;


}
