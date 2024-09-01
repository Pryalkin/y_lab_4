package com.pryalkin.client;

import com.pryalkin.dto.request.AuthServerRequestDTO;
import com.pryalkin.dto.request.AuthorizationRequestDTO;
import com.pryalkin.dto.response.AuthServerResponseDTO;
import com.pryalkin.dto.response.AuthorizationResponseDTO;

public interface SecurityClient {

    AuthorizationResponseDTO authorization(AuthorizationRequestDTO authorizationRequestDTO);
    AuthServerResponseDTO authorizationServer(AuthServerRequestDTO authServerRequestDTO);
    AuthServerResponseDTO registrationServer(AuthServerRequestDTO authServerRequestDTO);

}
