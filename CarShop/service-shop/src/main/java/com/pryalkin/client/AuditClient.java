package com.pryalkin.client;

import com.pryalkin.dto.request.LoggingUserRequestDTO;

public interface AuditClient {

    void send(LoggingUserRequestDTO loggingUserRequestDTO);

}
