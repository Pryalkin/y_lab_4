package com.pryalkin.web.auth;

import java.io.*;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.proxy.IProxy;
import com.pryalkin.proxy.Log;
import com.pryalkin.proxy.ProxyAuthService;
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
        HttpResponse httpResponse = null;
        IProxy<ServiceAuth, HttpResponse> iProxy = new ProxyAuthService(serviceAuth);
        try {
            httpResponse = iProxy.getResultMethod(null,"registration", userDTO);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpResponse);
        resp.setStatus(httpResponse.getHttpStatusCode());
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }



}