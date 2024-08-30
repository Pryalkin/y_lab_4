package com.pryalkin.service;

import com.pryalkin.dto.request.AuthServerRequestDTO;
import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.TokenRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.exception.model.*;
import com.pryalkin.model.Server;
import com.pryalkin.model.User;

public interface AuthService {

    User findByEmail(String username) throws EmailDontExistException;
    void registration(UserRequestDTO userRequestDTO) throws EmailExistException, PasswordException, EmailValidException;

    void registrationServer(AuthServerRequestDTO authServerRequestDTO);

    Server findByServerName(String serverName) throws EmailExistException;

    void validateCheckPassword(LoginUserRequestDTO userRequestDTO) throws PasswordException;

    UserResponseDTO getUserWithToken(TokenRequestDTO tokenRequestDTO) throws EmailDontExistException;

    UserResponseDTO getUserWithId(Long executorId) throws UserDontExistException;
}
