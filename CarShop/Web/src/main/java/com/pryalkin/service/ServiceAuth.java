package com.pryalkin.service;

import com.pryalkin.controller.ResponseEntity;
import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;

public interface ServiceAuth extends Service {

    UserResponseDTO registration(UserRequestDTO userDTO);

    String getAuthorization(LoginUserRequestDTO user);
    User getUser(User user);
    String encrypt(String text, int shift);

    String decrypt(String encryptedText, int shift);
    boolean checkToken(String authorities);
}
