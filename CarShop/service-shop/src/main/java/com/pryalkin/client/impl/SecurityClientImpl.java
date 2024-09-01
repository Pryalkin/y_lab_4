package com.pryalkin.client.impl;

import com.pryalkin.client.SecurityClient;
import com.pryalkin.dto.request.AuthServerRequestDTO;
import com.pryalkin.dto.request.AuthorizationRequestDTO;
import com.pryalkin.dto.request.TokenRequestDTO;
import com.pryalkin.dto.response.AuthServerResponseDTO;
import com.pryalkin.dto.response.AuthorizationResponseDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class SecurityClientImpl implements SecurityClient {

    private final WebClient webClient = WebClient.create();

    @Override
    public AuthorizationResponseDTO authorization(AuthorizationRequestDTO authorizationRequestDTO) {
        AuthorizationResponseDTO result = webClient.post()
                .uri("http://localhost:8080/auth/authorization/user")
                .bodyValue(authorizationRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationRequestDTO.getServiceToken())
                .retrieve()
                .bodyToMono(AuthorizationResponseDTO.class)
                .block();
        return result;
    }

    @Override
    public AuthServerResponseDTO registrationServer(AuthServerRequestDTO authServerRequestDTO) {
        AuthServerResponseDTO result = webClient.post()
                .uri("http://localhost:8080/auth/registration/server")
                .bodyValue(authServerRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(AuthServerResponseDTO.class)
                .block();
        return result;
    }

    @Override
    public UserResponseDTO getUserWithToken(TokenRequestDTO tokenRequestDTO, String token) {
        UserResponseDTO result = webClient.post()
                .uri("http://localhost:8080/shop/user/token")
                .bodyValue(tokenRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .block();
        return result;
    }

    @Override
    public UserResponseDTO getUserWithId(Long executorId, String token) {
        UserResponseDTO result = webClient.post()
                .uri("http://localhost:8080/shop/user/" + executorId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .block();
        return result;
    }

    @Override
    public AuthServerResponseDTO authorizationServer(AuthServerRequestDTO authServerRequestDTO) {
        AuthServerResponseDTO result = webClient.post()
                .uri("http://localhost:8080/auth/authorization/server")
                .bodyValue(authServerRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(AuthServerResponseDTO.class)
                .block();
        return result;
    }

}
