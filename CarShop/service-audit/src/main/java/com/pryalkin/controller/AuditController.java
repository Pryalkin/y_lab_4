package com.pryalkin.controller;

import com.pryalkin.dto.request.LoggingUserRequestDTO;
import com.pryalkin.mapper.LoggingUserMapper;
import com.pryalkin.model.LoggingUser;
import com.pryalkin.service.LoggingUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/audit")
@AllArgsConstructor
public class AuditController {

    private final LoggingUserService loggingUserService;
    private final LoggingUserMapper loggingUserMapper;

    @PostMapping("/send")
    public void send(@RequestBody LoggingUserRequestDTO loggingUserRequestDTO) {
        LoggingUser loggingUser = loggingUserMapper.loggingUserRequestDtoToLoggingUser(loggingUserRequestDTO);
        loggingUserService.send(loggingUser);
    }


}
