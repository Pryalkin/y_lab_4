package com.pryalkin.mapper;

import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.model.UserShop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserShopMapper {

    @Mapping(target="id", source="userResponseDTO.id")
    @Mapping(target="email", source="userResponseDTO.email")
    @Mapping(target="name", source="userResponseDTO.name")
    @Mapping(target="surname", source="userResponseDTO.surname")
    @Mapping(target="role", source="userResponseDTO.role")
    @Mapping(target="authorities", source="userResponseDTO.authorities")
    UserShop userResponseToUserShop(UserResponseDTO userResponseDTO);

    @Mapping(target="id", ignore = true)
    @Mapping(target="email", source="userShop.email")
    @Mapping(target="name", source="userShop.name")
    @Mapping(target="surname", source="userShop.surname")
    @Mapping(target="role", source="userShop.role")
    @Mapping(target="authorities", source="userShop.authorities")
    UserResponseDTO  userShopToUserShopResponseDTO(UserShop userShop);

}
