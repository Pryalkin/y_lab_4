package com.pryalkin.controller;

import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.service.ServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthController {

    private final ServiceAuth serviceAuth;

    @Autowired
    public AuthController(ServiceAuth serviceAuth) {
        this.serviceAuth = serviceAuth;
    }


    @PostMapping("/registration")
    public HttpResponse<UserResponseDTO> registration(@RequestBody UserRequestDTO userRequestDTO) {
        return serviceAuth.registration(userRequestDTO);
    }


    @GetMapping
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

}
