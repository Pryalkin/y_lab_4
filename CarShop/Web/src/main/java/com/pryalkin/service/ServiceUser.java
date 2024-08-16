package com.pryalkin.service;

import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;

import java.util.List;
import java.util.Set;

public interface ServiceUser extends Service{

    List<UserResponseDTO> getRegistrationClient();

    User getUser(User user);

    List<UserResponseDTO> getRegistrationManager();

}
