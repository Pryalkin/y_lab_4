package com.pryalkin.mapper.impl;

import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.mapper.UserMapper;
import com.pryalkin.model.User;

public class UserMapperImpl implements UserMapper {

    @Override
    public User userRequestDtoToUser(UserRequestDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setSurname(userDTO.getSurname());
        user.setRole(userDTO.getRole());
        return user;
    }

    @Override
    public User loginUserRequestDtoToUser(LoginUserRequestDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return user;
    }

    @Override
    public UserResponseDTO userToUserResponseDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setSurname(user.getSurname());
        userResponseDTO.setRole(user.getRole());
        return userResponseDTO;
    }
}
