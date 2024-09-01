package com.pryalkin.client;

import com.pryalkin.dto.request.AuthServerRequestDTO;
import com.pryalkin.dto.request.AuthorizationRequestDTO;
import com.pryalkin.dto.request.TokenRequestDTO;
import com.pryalkin.dto.response.AuthServerResponseDTO;
import com.pryalkin.dto.response.AuthorizationResponseDTO;
import com.pryalkin.dto.response.UserResponseDTO;

public interface SecurityClient {

    AuthorizationResponseDTO authorization(AuthorizationRequestDTO authorizationRequestDTO);

    AuthServerResponseDTO authorizationServer(AuthServerRequestDTO authServerRequestDTO);
    AuthServerResponseDTO registrationServer(AuthServerRequestDTO authServerRequestDTO);
    UserResponseDTO getUserWithToken(TokenRequestDTO tokenRequestDTO, String token);


    UserResponseDTO getUserWithId(Long executorId, String token);
}
