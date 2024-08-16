package com.pryalkin.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.annotation.Url;
import com.pryalkin.controller.Controller;
import com.pryalkin.controller.ResponseEntity;
import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.service.ServiceAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.stream.Collectors;


@WebServlet(name = "auth", value = "/auth/login")
public class AuthLoginServlet extends HttpServlet {

    private final ServiceAuth serviceAuth;

    public AuthLoginServlet() {
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
        LoginUserRequestDTO userDTO = objectMapper.readValue(reader, LoginUserRequestDTO.class);
        String token = serviceAuth.getAuthorization(userDTO);
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.setHeader("Authorization", "Bearer " + token);
        resp.getWriter().write(token);
    }

        public void destroy() {}
}
