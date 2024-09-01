package com.pryalkin.service;

import com.pryalkin.dto.request.LoggingUserRequestDTO;
import com.pryalkin.model.LoggingUser;

public interface LoggingUserService {
    void send(LoggingUser loggingUser);
}
