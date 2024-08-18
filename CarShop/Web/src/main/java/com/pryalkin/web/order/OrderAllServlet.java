package com.pryalkin.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pryalkin.dto.response.HttpResponse;
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
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet(name = "order_all", value = "/order/all")
public class OrderAllServlet extends HttpServlet {

    private final ServiceOrder serviceOrder;

    public OrderAllServlet() {
        try {
            this.serviceOrder = (ServiceOrder) Factory.initialization().getService("ServiceOrderImpl");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());

        HttpResponse httpResponse = null;
        IProxy<ServiceOrder, HttpResponse> iProxy = new ProxyOrderService(serviceOrder);
        try {
            httpResponse = iProxy.getResultMethod(token,"getOrders");
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMap = new ObjectMapper();
        String jsonString = objectMap.writeValueAsString(httpResponse);
        response.setStatus(httpResponse.getHttpStatusCode());
        response.setContentType("application/json");
        response.getWriter().write(jsonString);
    }

}
