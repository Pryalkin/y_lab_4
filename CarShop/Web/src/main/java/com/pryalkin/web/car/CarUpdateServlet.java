package com.pryalkin.web.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.factory.Factory;
import com.pryalkin.service.ServiceCar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(name = "car_all", value = "/car/update")
public class CarUpdateServlet extends HttpServlet {

    private final ServiceCar serviceCar;


    public CarUpdateServlet() {
        try {
            this.serviceCar = (ServiceCar) Factory.initialization().getService("ServiceCarImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        StringReader reader = new StringReader(requestBody);
        CarRequestDTO car = objectMapper.readValue(reader, CarRequestDTO.class);
        CarResponseDTO carResponseDTO = serviceCar.updateCar(car);
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(carResponseDTO);
        resp.setStatus(201);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonString);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }


    public void destroy() {
    }
}
