package com.pryalkin.controller.impl;

import com.pryalkin.annotation.Url;
import com.pryalkin.controller.ControllerOrder;
import com.pryalkin.model.Order;
import com.pryalkin.service.ServiceOrder;

@Controller(name = "/orders")
public class ControllerOrderImpl implements ControllerOrder {

    private final ServiceOrder serviceOrder;

    public ControllerOrderImpl(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @Url(name = "/create/one", method = "POST")
    public String createOrder(Order order){
        return serviceOrder.createOrder(order).toString();
    }

    @Url(name = "/get/all", method = "GET")
    public String getOrders(){
        return serviceOrder.getOrders().toString();
    }

    @Url(name = "/find/client", method = "POST")
    public String findOrderClient(Order order){
        return serviceOrder.findOrderClient(order).toString();
    }

//    @Url(name = "/logging", method = "POST")
//    public String logging(User user){
//        return service.logging(user).toString();
//    }
}
