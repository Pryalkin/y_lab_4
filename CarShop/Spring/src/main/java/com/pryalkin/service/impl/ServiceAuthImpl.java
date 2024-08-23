package com.pryalkin.service.impl;

import com.pryalkin.dao.UserDao;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.mapper.UserMapper;
import com.pryalkin.mapper.impl.UserMapperImpl;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.pryalkin.emun.StatusCode.CREATE;
import static com.pryalkin.emun.StatusCode.OK;

@Service
public class ServiceAuthImpl implements ServiceAuth {

    private final UserDao userDao;

    @Autowired
    public ServiceAuthImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public HttpResponse<UserResponseDTO> registration(UserRequestDTO userDTO) {
        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.userRequestDtoToUser(userDTO);
        userDao.save(user);
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDto(user);
        Map<String, UserResponseDTO> response = new HashMap<>();
        response.put(userResponseDTO.getClass().getSimpleName(), userResponseDTO);
        HttpResponse<UserResponseDTO> httpResponse = new HttpResponse(CREATE.getCode(), CREATE.name(), "Пользователь успешно зарегистрирован!", response);
        return httpResponse;
    }

//    @Override
//    public HttpResponse<MessageResponse> getAuthorization(LoginUserRequestDTO user) {
//        boolean authorization = repositoryUser.findUsers().stream()
//                .anyMatch(u -> u.getUsername().equals(user.getUsername())
//                        && u.getPassword().equals(user.getPassword()));
//        Map<String, MessageResponse> response = new HashMap<>();
//        MessageResponse messageResponse = new MessageResponse();
//        if (authorization){
//            UserMapper userMapper = new UserMapperImpl();
//            User us = userMapper.loginUserRequestDtoToUser(user);
//            User generateTokenForUser = this.getUser(us);
//            String token = encrypt(generateTokenForUser.getUsername() + "." + generateTokenForUser.getPassword(), 1);
//            messageResponse.setMessage(token);
//            response.put(response.getClass().getSimpleName(), messageResponse);
//            HttpResponse<MessageResponse> answer = new HttpResponse<>(OK.getCode(), OK.name(), "Пользователь успешно авторизован!", response);
//            return answer;
//        }
//        messageResponse.setMessage(Boolean.FALSE.toString());
//        response.put(response.getClass().getName(), messageResponse);
//        HttpResponse<MessageResponse> answer = new HttpResponse<>(OK.getCode(), OK.name(), "Токен не валиден!", response);
//        return answer;
//    }
//
//    @Override
//    public User getUser(User user) {
//        return repositoryUser.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
//
//    @Override
//    public String encrypt(String text, int shift) {
//        StringBuilder result = new StringBuilder();
//        for (char ch : text.toCharArray()) {
//            if (Character.isLetter(ch)) {
//                boolean isUpperCase = Character.isUpperCase(ch);
//                char base = isUpperCase ? 'A' : 'a';
//                int shiftedIndex = (ch - base + shift) % 26;
//                result.append((char) (base + shiftedIndex));
//            } else {
//                result.append(ch);
//            }
//        }
//        return result.toString();
//    }
//
//    @Override
//    public String decrypt(String encryptedText, int shift) {
//        return encrypt(encryptedText, -shift);
//    }
//
//    @Override
//    public boolean checkToken(String authorities) {
//        return getUserByToken(authorities) == null;
//    }
//
//    @Override
//    public User getUserByToken(String token) {
//        String usernameAndPassword = decrypt(token, 1);
//        int index = usernameAndPassword.indexOf('.');
//        String username = usernameAndPassword.substring(0, index);
//        String password = usernameAndPassword.substring(index+1);
//        User user = repositoryUser.findUserByUsernameAndPassword(username, password);
//        return user;
//    }
}
