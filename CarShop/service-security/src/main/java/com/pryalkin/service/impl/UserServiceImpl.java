package com.pryalkin.service.impl;

import com.pryalkin.dao.UserDao;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.enumeration.Role;
import com.pryalkin.exception.model.EmailDontExistException;
import com.pryalkin.mapper.UserMapper;
import com.pryalkin.model.User;
import com.pryalkin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pryalkin.constant.UserImplConstant.EMAIL_DONT_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;


    @Override
    public List<UserResponseDTO> getRegistrationClient() {
        return userDao.findByRole(Role.ROLE_USER.name()).get()
                .stream().map(userMapper::userToUserResponseDto).toList();
    }

    @Override
    public List<UserResponseDTO> getRegistrationManager() {
        return userDao.findByRole(Role.ROLE_MANAGER.name()).get()
                .stream().map(userMapper::userToUserResponseDto).toList();
    }

    @Override
    public UserResponseDTO installRole(Long userId, String role) throws EmailDontExistException {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new EmailDontExistException(EMAIL_DONT_ALREADY_EXISTS));
        switch (role) {
            case "ROLE_USER" -> {
                user.setRole(Role.ROLE_USER.name());
                user.setAuthorities(Role.ROLE_USER.getAuthorities());
            }
            case "ROLE_MANAGER" -> {
                user.setRole(Role.ROLE_MANAGER.name());
                user.setAuthorities(Role.ROLE_MANAGER.getAuthorities());
            }
            case "ROLE_ADMIN" -> {
                user.setRole(Role.ROLE_ADMIN.name());
                user.setAuthorities(Role.ROLE_ADMIN.getAuthorities());
            }
        }
        userDao.update(user);
        return userMapper.userToUserResponseDto(user);
    }

}
