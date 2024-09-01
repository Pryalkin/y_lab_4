package com.pryalkin.service;

import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.exception.model.EmailDontExistException;
import com.pryalkin.model.User;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getRegistrationClient();

    List<UserResponseDTO> getRegistrationManager();

    UserResponseDTO installRole(Long userId, String role) throws EmailDontExistException;
}
