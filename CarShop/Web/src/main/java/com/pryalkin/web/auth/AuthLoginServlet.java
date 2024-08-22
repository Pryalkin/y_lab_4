package com.pryalkin.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.LoginUserRequestDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.factory.Factory;
import com.pryalkin.proxy.IProxy;
import com.pryalkin.proxy.ProxyAuthService;
import com.pryalkin.service.ServiceAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
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
        HttpResponse httpResponse = null;
        IProxy<ServiceAuth, HttpResponse> iProxy = new ProxyAuthService(serviceAuth);
        try {
            httpResponse = iProxy.getResultMethod(null,"getAuthorization", userDTO);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpResponse);
        resp.setStatus(httpResponse.getHttpStatusCode());
        resp.setContentType("application/json");
        resp.setHeader("Authorization", "Bearer " + httpResponse.getBody().get("String"));
        resp.getWriter().write(jsonString);
    }

}
