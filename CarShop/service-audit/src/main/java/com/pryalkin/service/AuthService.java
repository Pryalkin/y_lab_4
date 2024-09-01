package com.pryalkin.service;


import com.pryalkin.dto.request.AuthServerRequestDTO;

public interface AuthService {

    String authorization(AuthServerRequestDTO authServerRequestDTO);
    void registration();

    String getToken();
}
