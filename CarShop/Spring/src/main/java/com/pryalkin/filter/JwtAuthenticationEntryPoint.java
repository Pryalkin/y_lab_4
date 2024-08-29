package com.pryalkin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.response.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.pryalkin.constant.SecurityConstant.ACCESS_DENIED_MESSAGE;
import static com.pryalkin.constant.SecurityConstant.FORBIDDEN_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Map<String, String> resp = new HashMap<>();
        resp.put(String.class.getSimpleName(), FORBIDDEN_MESSAGE);
        HttpResponse httpResponse = new HttpResponse(FORBIDDEN.value(), FORBIDDEN.name(), FORBIDDEN.getReasonPhrase().toUpperCase(), resp);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
