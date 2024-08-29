package com.pryalkin.service;

import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;

import java.util.List;

public interface ServiceUser {

    HttpResponse<List<UserResponseDTO>> getRegistrationClient();

    User getUser(User user);

    HttpResponse<List<UserResponseDTO>> getRegistrationManager();

}
