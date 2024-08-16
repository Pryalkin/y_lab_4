package com.pryalkin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.factory.Factory;
import com.pryalkin.service.ServiceAuth;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = {"/user/client"})
public class MyFilter implements Filter {

    private final ServiceAuth serviceAuth;

    public MyFilter() {
        try {
            this.serviceAuth = (ServiceAuth) Factory.initialization().getService("ServiceAuthImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpResponse.sendRedirect("/error");
            return;
        }
        String token = authorizationHeader.substring("Bearer ".length());
        if(serviceAuth.checkToken(token)){
            httpResponse.sendRedirect("/error");
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
