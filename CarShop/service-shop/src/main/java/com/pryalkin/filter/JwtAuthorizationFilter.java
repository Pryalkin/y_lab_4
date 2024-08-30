package com.pryalkin.filter;

import com.pryalkin.client.SecurityClient;
import com.pryalkin.dto.request.AuthorizationRequestDTO;
import com.pryalkin.dto.response.AuthorizationResponseDTO;
import com.pryalkin.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final SecurityClient securityClient;
    private final AuthService authService;
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(TOKEN_PREFIX.length());
        AuthorizationRequestDTO requestDTO = new AuthorizationRequestDTO();
        requestDTO.setUserToken(token);
        System.out.println("TOKEN SERVER: " + authService.getToken());
        requestDTO.setServiceToken(authService.getToken());
        AuthorizationResponseDTO responseDTO = securityClient.authorization(requestDTO);
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                new UsernamePasswordAuthenticationToken(null, null, null);
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
        filterChain.doFilter(request, response);
    }

}
