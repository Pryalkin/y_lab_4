package com.pryalkin.web.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.response.HttpResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;

@WebServlet(name = "error", value = "/error")
public class Error401Servlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpResponse httpRes = new HttpResponse(HttpStatus.SC_UNAUTHORIZED, "Unauthorized", "Авторизуйтесь!");
        response.setContentType("application/json");
        response.setStatus(httpRes.getHttpStatusCode());
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpRes);
        response.getWriter().write(jsonString);
    }

}
