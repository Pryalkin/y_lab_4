package com.pryalkin.controller;

import com.pryalkin.dto.request.TokenRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.exception.model.EmailDontExistException;
import com.pryalkin.exception.model.UserDontExistException;
import com.pryalkin.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final AuthService authService;

    @PostMapping("/user/token")
    public ResponseEntity<UserResponseDTO> getUserWithToken(@RequestBody TokenRequestDTO tokenRequestDTO) throws EmailDontExistException {
        return new ResponseEntity<>(authService.getUserWithToken(tokenRequestDTO), OK);
    }

    @PostMapping("/user/{executorId}")
    public ResponseEntity<UserResponseDTO> getUserWithId(@PathVariable Long executorId) throws UserDontExistException {
        return new ResponseEntity<>(authService.getUserWithId(executorId), OK);
    }


}