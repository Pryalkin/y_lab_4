package com.pryalkin.service.impl;

import com.pryalkin.client.SecurityClient;
import com.pryalkin.dao.ServerDao;
import com.pryalkin.dto.request.AuthServerRequestDTO;
import com.pryalkin.dto.response.AuthServerResponseDTO;
import com.pryalkin.model.Server;
import com.pryalkin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${app.name}")
    private String serverName;
    @Value("${app.password}")
    private String serverPassword;
    private final SecurityClient securityClient;
    private final ServerDao serverDao;


    @Override
    public String authorization(AuthServerRequestDTO authServerRequestDTO) {
        AuthServerResponseDTO authServerResponseDTO = securityClient.authorizationServer(authServerRequestDTO);
        return authServerResponseDTO.getToken();
    }

    @Override
    public void registration() {
        AuthServerRequestDTO authServerRequestDTO = new AuthServerRequestDTO();
        authServerRequestDTO.setServerName(serverName);
        authServerRequestDTO.setServerPassword(serverPassword);
        securityClient.registrationServer(authServerRequestDTO);
        String token = authorization(authServerRequestDTO);
        Server server = new Server();
        server.setServername(serverName);
        server.setServerpassword(serverPassword);
        server.setToken(token);
        serverDao.save(server);
    }

    @Override
    public String getToken() {
        return serverDao.findByServernameAndServerpassword(serverName, serverPassword)
                .orElseThrow().getToken();
    }

}
