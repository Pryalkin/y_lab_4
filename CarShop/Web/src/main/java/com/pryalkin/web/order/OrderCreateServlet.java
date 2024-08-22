package com.pryalkin.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.proxy.IProxy;
import com.pryalkin.proxy.ProxyCarService;
import com.pryalkin.proxy.ProxyOrderService;
import com.pryalkin.service.ServiceCar;
import com.pryalkin.service.ServiceOrder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(name = "order", value = "/order/create")
public class OrderCreateServlet extends HttpServlet {

    private final ServiceOrder serviceOrder;

    public OrderCreateServlet() {
        try {
            this.serviceOrder = (ServiceOrder) Factory.initialization().getService("ServiceOrderImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        StringReader reader = new StringReader(requestBody);
        OrderRequestDTO newOrder = objectMapper.readValue(reader, OrderRequestDTO.class);
        HttpResponse httpResponse = null;
        IProxy<ServiceOrder, HttpResponse> iProxy = new ProxyOrderService(serviceOrder);
        try {
            httpResponse = iProxy.getResultMethod(token,"createOrder", newOrder);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpResponse);
        resp.setStatus(httpResponse.getHttpStatusCode());
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }


    public void destroy() {
    }

}
