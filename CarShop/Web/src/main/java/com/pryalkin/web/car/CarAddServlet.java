package com.pryalkin.web.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.request.UserRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.service.ServiceCar;
import com.pryalkin.service.ServiceUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "car_add", value = "/car/add")
public class CarAddServlet extends HttpServlet {

    private final ServiceCar serviceCar;

    public CarAddServlet() {
        try {
            this.serviceCar = (ServiceCar) Factory.initialization().getService("ServiceCarImpl");
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
        NewCarRequestDTO newCar = objectMapper.readValue(reader, NewCarRequestDTO.class);
        CarResponseDTO carResponseDTO = serviceCar.addCar(newCar);
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(carResponseDTO);
        resp.setStatus(201);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }


    public void destroy() {
    }
}
