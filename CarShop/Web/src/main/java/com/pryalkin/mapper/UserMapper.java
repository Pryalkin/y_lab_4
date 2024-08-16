package com.pryalkin.mapper;

import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRequestDtoToUser(UserRequestDTO userDTO);

    User loginUserRequestDtoToUser(LoginUserRequestDTO userDTO);

    UserResponseDTO userToUserResponseDto(User user);

}
