package com.pryalkin.web.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.request.CarRequestDTO;
import com.pryalkin.dto.request.NewCarRequestDTO;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.factory.Factory;
import com.pryalkin.proxy.IProxy;
import com.pryalkin.proxy.ProxyCarService;
import com.pryalkin.service.ServiceCar;
import jakarta.servlet.ServletException;
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
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        StringReader reader = new StringReader(requestBody);
        CarRequestDTO car = objectMapper.readValue(reader, CarRequestDTO.class);
        HttpResponse httpResponse = null;
        IProxy<ServiceCar, HttpResponse> iProxy = new ProxyCarService(serviceCar);
        try {
            httpResponse = iProxy.getResultMethod(token,"updateCar", car);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpResponse);
        resp.setStatus(httpResponse.getHttpStatusCode());
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
