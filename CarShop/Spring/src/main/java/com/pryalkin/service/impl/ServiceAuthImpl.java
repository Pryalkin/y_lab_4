package com.pryalkin.service.impl;

import com.pryalkin.dao.UserDao;
import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.exceptions.model.UsernameExistException;
import com.pryalkin.mapper.UserMapper;
import com.pryalkin.mapper.impl.UserMapperImpl;
import com.pryalkin.model.User;
import com.pryalkin.model.UserPrincipal;
import com.pryalkin.service.ServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.pryalkin.emun.Role.ROLE_ADMIN;
import static com.pryalkin.emun.Role.ROLE_USER;
import static com.pryalkin.emun.StatusCode.CREATE;
import static com.pryalkin.emun.StatusCode.OK;

@Service
@Qualifier("userDetailsService")
public class ServiceAuthImpl implements ServiceAuth, UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public ServiceAuthImpl(UserDao userDao,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found by username: " + username));
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }

    @Override
    public HttpResponse<UserResponseDTO> registration(UserRequestDTO userDTO) throws UsernameExistException {
        validateNewUsername(userDTO);
        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.userRequestDtoToUser(userDTO);
        user.setPassword(encodePassword(user.getPassword()));
        if (userDao.findAll().isEmpty()) {
            user.setRole(ROLE_ADMIN.name());
            user.setAuthorities(ROLE_ADMIN.getAuthorities());
        } else {
            user.setRole(ROLE_USER.name());
            user.setAuthorities(ROLE_USER.getAuthorities());
        }
        userDao.save(user);
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDto(user);
        Map<String, UserResponseDTO> response = new HashMap<>();
        response.put(userResponseDTO.getClass().getSimpleName(), userResponseDTO);
        HttpResponse<UserResponseDTO> httpResponse = new HttpResponse(CREATE.getCode(), CREATE.name(), "Пользователь успешно зарегистрирован!", response);
        return httpResponse;
    }

//    @Override
//    public HttpResponse<MessageResponse> getAuthorization(LoginUserRequestDTO user) {
//        boolean authorization = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword()).isPresent();
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

    @Override
    public User getUser(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword()).get();
    }

    @Override
    public User findByUsername(String username) throws UsernameExistException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameExistException("Username already exists"));
    }

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
//        User user = userDao.findByUsernameAndPassword(username, password).get();
//        return user;
//    }

    private void validateNewUsername(UserRequestDTO userDTO) throws UsernameExistException{
        if (userDao.findByUsername((userDTO.getUsername())).isPresent()){
            throw new UsernameExistException("Username already exists");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
