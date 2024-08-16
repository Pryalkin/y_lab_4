package com.pryalkin.controller;

import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;

public interface ControllerAuth extends Controller{

    ResponseEntity<UserResponseDTO> registration(UserRequestDTO userDTO);
    ResponseEntity<String> getAuthorization(LoginUserRequestDTO user);
}
