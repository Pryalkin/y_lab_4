package com.pryalkin.model;

import lombok.Data;

@Data
public class LoggingUser {

    private Long id;
    private UserAudit userAudit;
    private String action;
    private String date;

}
