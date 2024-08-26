package com.pryalkin.controller;

import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.MessageResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.service.ServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ServiceAuth serviceAuth;

    @Autowired
    public AuthController(ServiceAuth serviceAuth) {
        this.serviceAuth = serviceAuth;
    }

    @PostMapping("/registration")
    public HttpResponse<UserResponseDTO> registration(@RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("Controller - registration");
        return serviceAuth.registration(userRequestDTO);
    }

    @PostMapping("/login")
    public HttpResponse<MessageResponse> login(@RequestBody LoginUserRequestDTO userRequestDTO) {
        return serviceAuth.getAuthorization(userRequestDTO);
    }

}
