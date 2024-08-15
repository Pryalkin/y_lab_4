package com.pryalkin.web;

import java.io.*;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http//localhost:8081/auth/registration");
//        httpPost.setHeader("Content-type", "application/json");
//        StringEntity entity = new StringEntity(requestBody);
//        httpPost.setEntity(entity);
//        client.execute(httpPost);
//        client.close();
//        System.out.println(requestBody);
//        response.setContentType("application/json");
//        response.setHeader("method", "POST");
//        response.sendRedirect("http://localhost:8080/auth/registration");

//        try (CloseableHttpClient client = HttpClients.createDefault();
//             HttpPost httpPost = new HttpPost("http://other-app.com/other-servlet")) {
//            httpPost.setHeader("Content-type", "application/json");
//            StringEntity entity = new StringEntity("{\"data\": \"some data\"}");
//            httpPost.setEntity(entity);
//            client.execute(httpPost);
//        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello Servlet. I AM POST");
//        resp.setContentType("application/json");
//        resp.setHeader("Method", "POST");
//        resp.sendRedirect("http://localhost:8080/auth/registration");
    }

    public void destroy() {
    }
}