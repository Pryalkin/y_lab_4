package com.pryalkin.mapper;

import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;

public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRequestDtoToUser(UserRequestDTO userDTO);

    User loginUserRequestDtoToUser(LoginUserRequestDTO userDTO);

    UserResponseDTO userToUserResponseDto(User user);

}
