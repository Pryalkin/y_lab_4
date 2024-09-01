package com.pryalkin.dto.request;

import com.pryalkin.model.UserAudit;
import lombok.Data;

@Data
public class LoggingUserRequestDTO {

    private UserAudit userAudit;
    private String action;
    private String date;

}
