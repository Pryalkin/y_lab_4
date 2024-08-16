package com.pryalkin.web.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.response.CarResponseDTO;
import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.factory.Factory;
import com.pryalkin.service.ServiceCar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "car_delete", value = "/car/find")
public class CarFindServlet extends HttpServlet {


    private final ServiceCar serviceCar;

    public CarFindServlet() {
        try {
            this.serviceCar = (ServiceCar) Factory.initialization().getService("ServiceCarImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        List<CarResponseDTO> carResponseDTOs = serviceCar.findCarBrandModel(brand, model);
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(carResponseDTOs);
        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }


    public void destroy() {

    }
}
