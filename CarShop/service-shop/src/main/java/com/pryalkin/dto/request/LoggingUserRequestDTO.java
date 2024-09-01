package com.pryalkin.dto.request;

import com.pryalkin.dto.UserAudit;
import lombok.Data;

import java.util.Date;

@Data
public class LoggingUserRequestDTO {

    private UserAudit userAudit;
    private String action;
    private Date date;

}
