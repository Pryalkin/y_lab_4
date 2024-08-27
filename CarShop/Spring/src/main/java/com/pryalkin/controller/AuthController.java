package com.pryalkin.controller;

import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.LoginUserResponseDTO;
import com.pryalkin.exceptions.ExceptionHandling;
import com.pryalkin.exceptions.model.UsernameExistException;
import com.pryalkin.model.User;
import com.pryalkin.model.UserPrincipal;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.pryalkin.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/auth")
public class AuthController extends ExceptionHandling {

    private final ServiceAuth serviceAuth;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(ServiceAuth serviceAuth,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.serviceAuth = serviceAuth;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpResponse> registration(@RequestBody UserRequestDTO userRequestDTO) throws UsernameExistException {
        return new ResponseEntity<>(serviceAuth.registration(userRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) throws UsernameExistException {
        authenticate(userRequestDTO.getUsername(), userRequestDTO.getPassword());
        User loginUser = serviceAuth.findByUsername(userRequestDTO.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        LoginUserResponseDTO loginUserAnswerDTO = new LoginUserResponseDTO();
        loginUserAnswerDTO.setUsername(loginUser.getUsername());
        loginUserAnswerDTO.setRole(loginUser.getRole());
        loginUserAnswerDTO.setAuthorities(loginUser.getAuthorities());
        return new ResponseEntity<>(loginUserAnswerDTO, jwtHeader, HttpStatus.OK);
    }


    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

}
