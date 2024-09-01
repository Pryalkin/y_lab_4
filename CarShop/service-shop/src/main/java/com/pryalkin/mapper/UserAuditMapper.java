package com.pryalkin.mapper;

import com.pryalkin.dto.UserAudit;
import com.pryalkin.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAuditMapper {

    @Mapping(target="id", source="userResponseDTO.id")
    @Mapping(target="name", source="userResponseDTO.name")
    @Mapping(target="surname", source="userResponseDTO.surname")
    UserAudit userResponseDtoToUserAudit(UserResponseDTO userResponseDTO);

}
