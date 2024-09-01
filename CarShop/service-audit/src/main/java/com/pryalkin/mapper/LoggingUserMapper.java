package com.pryalkin.mapper;

import com.pryalkin.dto.request.LoggingUserRequestDTO;
import com.pryalkin.model.LoggingUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoggingUserMapper {

    @Mapping(target="id", ignore = true)
    @Mapping(target="userAudit", source="loggingUserRequestDTO.userAudit")
    @Mapping(target="action", source="loggingUserRequestDTO.action")
    @Mapping(target="date", source="loggingUserRequestDTO.date")
    LoggingUser loggingUserRequestDtoToLoggingUser(LoggingUserRequestDTO loggingUserRequestDTO);

}
