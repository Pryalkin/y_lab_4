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

//@Service
//public class ServiceUserImpl implements ServiceUser {
//
//    private final UserDao userDao;
//
//    public ServiceUserImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }

//    @Override
//    public User getUser(User user) {
//        return repositoryUser.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
//
//    @Override
//    public HttpResponse<List<UserResponseDTO>> getRegistrationClient() {
//        List<UserResponseDTO> userResponseDTOs = repositoryUser.findUsers().stream()
//                .filter(user -> user.getRole().equals(Role.CLIENT.name()))
//                .map(new UserMapperImpl()::userToUserResponseDto).toList();
//        Map<String, List<UserResponseDTO>> response = new HashMap<>();
//        response.put(UserResponseDTO.class.getSimpleName(), userResponseDTOs);
//        return new HttpResponse<List<UserResponseDTO>>(StatusCode.OK.getCode(), StatusCode.OK.name(),
//                "Список клиентов приложения!", response);
//    }
//
//    @Override
//    public List<UserResponseDTO> getRegistrationManager() {
//        return repositoryUser.findUsers().stream()
//                .filter(user -> user.getRole().equals(Role.MANAGER.name()))
//                .map(new UserMapperImpl()::userToUserResponseDto).toList();
//    }

//}
