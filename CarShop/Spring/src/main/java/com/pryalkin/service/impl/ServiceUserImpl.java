package com.pryalkin.service.impl;

import com.pryalkin.dao.UserDao;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.emun.Role;
import com.pryalkin.emun.StatusCode;
import com.pryalkin.mapper.impl.UserMapperImpl;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceUserImpl implements ServiceUser {

    private final UserDao userDao;

    public ServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword()).get();
    }

    @Override
    public HttpResponse<List<UserResponseDTO>> getRegistrationClient() {
        List<UserResponseDTO> userResponseDTOs = userDao.findByRole(Role.ROLE_USER.name()).get()
                .stream().map(new UserMapperImpl()::userToUserResponseDto).toList();
        Map<String, List<UserResponseDTO>> response = new HashMap<>();
        response.put(UserResponseDTO.class.getSimpleName(), userResponseDTOs);
        return new HttpResponse<List<UserResponseDTO>>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Список клиентов приложения!", response);
    }

    @Override
    public HttpResponse<List<UserResponseDTO>> getRegistrationManager() {
        List<UserResponseDTO> userResponseDTOs = userDao.findByRole(Role.ROLE_MANAGER.name()).get()
                .stream().map(new UserMapperImpl()::userToUserResponseDto).toList();
        Map<String, List<UserResponseDTO>> response = new HashMap<>();
        response.put(UserResponseDTO.class.getSimpleName(), userResponseDTOs);
        return new HttpResponse<List<UserResponseDTO>>(StatusCode.OK.getCode(), StatusCode.OK.name(),
                "Список клиентов приложения!", response);
    }

}
