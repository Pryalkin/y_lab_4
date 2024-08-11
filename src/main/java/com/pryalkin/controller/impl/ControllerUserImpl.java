package com.pryalkin.controller.impl;

import com.pryalkin.annotation.Url;
import com.pryalkin.controller.ControllerUser;
import com.pryalkin.service.ServiceUser;

@Controller(name = "/users")
public class ControllerUserImpl implements ControllerUser {

    private final ServiceUser serviceUser;

    public ControllerUserImpl(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Url(name = "/get/registration/client", method = "GET")
    @Override
    public String getRegistrationClient(){
        return serviceUser.getRegistrationClient().toString();
    }

    @Url(name = "/get/registration/manager", method = "GET")
    @Override
    public String getRegistrationManager(){
        return serviceUser.getRegistrationManager().toString();
    }
}
