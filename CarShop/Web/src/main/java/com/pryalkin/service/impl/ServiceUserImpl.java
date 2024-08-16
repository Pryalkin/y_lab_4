package com.pryalkin.service.impl;

import com.pryalkin.annotation.Service;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.emun.Role;
import com.pryalkin.mapper.UserMapper;
import com.pryalkin.mapper.impl.UserMapperImpl;
import com.pryalkin.model.User;
import com.pryalkin.repository.RepositoryUser;
import com.pryalkin.service.ServiceUser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceUserImpl implements ServiceUser {

    private final RepositoryUser repositoryUser;

    public ServiceUserImpl(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Override
    public User getUser(User user) {
        return repositoryUser.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public List<UserResponseDTO> getRegistrationClient() {
        return repositoryUser.findUsers().stream()
                .filter(user -> user.getRole().equals(Role.CLIENT.name()))
                .map(new UserMapperImpl()::userToUserResponseDto).toList();
    }

    @Override
    public List<UserResponseDTO> getRegistrationManager() {
        return repositoryUser.findUsers().stream()
                .filter(user -> user.getRole().equals(Role.MANAGER.name()))
                .map(new UserMapperImpl()::userToUserResponseDto).toList();
    }

}
