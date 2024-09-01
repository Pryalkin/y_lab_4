package com.pryalkin.client.impl;

import com.pryalkin.client.AuditClient;
import com.pryalkin.dto.request.LoggingUserRequestDTO;
import com.pryalkin.dto.request.TokenRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class AuditClientImpl implements AuditClient {

    private final WebClient webClient = WebClient.create();
    private final AuthService authService;


    @Override
    public void send(LoggingUserRequestDTO loggingUserRequestDTO) {
        UserResponseDTO result = webClient.post()
                .uri("http://localhost:8082/audit/send")
                .bodyValue(loggingUserRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.getToken())
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .block();
    }
}
