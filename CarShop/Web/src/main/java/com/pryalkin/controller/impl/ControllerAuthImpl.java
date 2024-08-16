package com.pryalkin.controller.impl;

import com.pryalkin.annotation.Controller;
import com.pryalkin.annotation.Url;
import com.pryalkin.controller.ControllerAuth;
import com.pryalkin.controller.ResponseEntity;
import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.emun.StatusCode;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceAuth;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

@Controller(name = "/auth")
public class ControllerAuthImpl implements ControllerAuth {

    private final ServiceAuth serviceAuth;

    public ControllerAuthImpl(ServiceAuth serviceAuth) {
        this.serviceAuth = serviceAuth;
    }

    // service SAFETY
    @Url(name = "/registration")
    @Override
    public ResponseEntity<UserResponseDTO> registration(UserRequestDTO userDTO){
        UserResponseDTO userResponseDTO = serviceAuth.registration(userDTO);
        return new ResponseEntity<>(userResponseDTO, StatusCode.OK);
    }

    @Url(name = "/login")
    @Override
    public ResponseEntity<String>  getAuthorization(LoginUserRequestDTO user){
        System.out.println("LOGIN");
        String answer = serviceAuth.getAuthorization(user);
        Header header = new BasicHeader("Authorization", "Bearer " + answer);
        return new ResponseEntity<>(answer, StatusCode.OK, header);
    }

}
