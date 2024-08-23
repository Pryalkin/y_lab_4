package com.pryalkin.service;

import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;

public interface ServiceAuth {

    HttpResponse<UserResponseDTO> registration(UserRequestDTO userDTO);

    HttpResponse<MessageResponse> getAuthorization(LoginUserRequestDTO user);
    User getUser(User user);
    String encrypt(String text, int shift);

    String decrypt(String encryptedText, int shift);
    boolean checkToken(String authorities);

    User getUserByToken(String token);
}
