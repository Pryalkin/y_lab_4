package com.pryalkin.mapper;

import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target="email", source="userDTO.email")
    @Mapping(target="password", source="userDTO.password")
    @Mapping(target="name", source="userDTO.name")
    @Mapping(target="surname", source="userDTO.surname")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userRequestDtoToUser(UserRequestDTO userDTO);

    @Mapping(target="id", source="user.id")
    @Mapping(target="email", source="user.email")
    @Mapping(target="name", source="user.name")
    @Mapping(target="surname", source="user.surname")
    @Mapping(target="role", source="user.role")
    @Mapping(target="authorities", source="user.authorities")
    UserResponseDTO userToUserResponseDto(User user);

}
