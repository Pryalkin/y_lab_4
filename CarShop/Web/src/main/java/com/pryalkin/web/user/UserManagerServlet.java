package com.pryalkin.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.proxy.IProxy;
import com.pryalkin.proxy.ProxyUserService;
import com.pryalkin.service.ServiceUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "user_manager", value = "/user/manager")
public class UserManagerServlet extends HttpServlet {

    private final ServiceUser serviceUser;

    public UserManagerServlet() {
        try {
            this.serviceUser = (ServiceUser) Factory.initialization().getService("ServiceUserImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpResponse httpResponse = null;
        IProxy<ServiceUser, HttpResponse> iProxy = new ProxyUserService(serviceUser);
        try {
            httpResponse = iProxy.getResultMethod(null, "getRegistrationManager");
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpResponse);
        response.setStatus(httpResponse.getHttpStatusCode());
        response.setContentType("application/json");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }


    public void destroy() {
    }
}
