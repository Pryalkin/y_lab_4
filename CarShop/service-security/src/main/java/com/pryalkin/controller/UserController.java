package com.pryalkin.controller;

import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.exception.ExceptionHandling;
import com.pryalkin.exception.model.EmailDontExistException;
import com.pryalkin.exception.model.EmailExistException;
import com.pryalkin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<UserResponseDTO>> getClients() {
        return new ResponseEntity<>(userService.getRegistrationClient(), HttpStatus.OK);
    }

    @GetMapping("/managers")
    public ResponseEntity<List<UserResponseDTO>> getManagers() {
        return new ResponseEntity<>(userService.getRegistrationManager(), HttpStatus.OK);
    }

    @PutMapping("/install/role")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<UserResponseDTO> installRole(@RequestParam Long userId,
                                                       @RequestParam String role) throws EmailExistException, EmailDontExistException {
        return new ResponseEntity<>(userService.installRole(userId, role), OK);
    }


}
