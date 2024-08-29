package com.pryalkin.config;

import com.pryalkin.filter.JwtAuthorizationFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        // Создание контекста Spring
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SecurityConfiguration.class);
        context.scan("com.pryalkin");

        // Регистрация слушателя контекста
        container.addListener(new ContextLoaderListener(context));

        // Создание DispatcherServlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("mvc", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }
}



