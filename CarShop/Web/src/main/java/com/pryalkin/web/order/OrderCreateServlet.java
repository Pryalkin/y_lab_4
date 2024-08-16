package com.pryalkin.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.request.OrderRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.OrderResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.service.ServiceCar;
import com.pryalkin.service.ServiceOrder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
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
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        StringReader reader = new StringReader(requestBody);
        OrderRequestDTO newOrder = objectMapper.readValue(reader, OrderRequestDTO.class);
        OrderResponseDTO orderResponseDTO = serviceOrder.createOrder(newOrder);
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(orderResponseDTO);
        resp.setStatus(201);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }


    public void destroy() {
    }

}
