package com.pryalkin.controller;

import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.exceptions.ExceptionHandling;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling {

    private final ServiceUser serviceUser;

    @Autowired
    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @GetMapping("/clients")
    public HttpResponse<List<UserResponseDTO>> getClients() {
        return serviceUser.getRegistrationClient();
    }

    @GetMapping("/managers")
    public HttpResponse<List<UserResponseDTO>> getManagers() {
        return serviceUser.getRegistrationManager();
    }

}
