package com.pryalkin.web.auth;

import java.io.*;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.proxy.Log;
import com.pryalkin.proxy.Proxy;
import com.pryalkin.service.ServiceAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "auth_reg", value = "/auth/registration")
public class AuthRegistrationServlet extends HttpServlet {

    private final ServiceAuth serviceAuth;

    public AuthRegistrationServlet() {
        try {
            this.serviceAuth = (ServiceAuth) Factory.initialization().getService("ServiceAuthImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        StringReader reader = new StringReader(requestBody);
        UserRequestDTO userDTO = objectMapper.readValue(reader, UserRequestDTO.class);
        UserResponseDTO userResponseDTO = null;
        try {
            userResponseDTO = (UserResponseDTO) Proxy
                    .getResultMethod(serviceAuth, new Log(),"registration", userDTO);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(userResponseDTO);
        resp.setStatus(201);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }


    public void destroy() {
    }

}