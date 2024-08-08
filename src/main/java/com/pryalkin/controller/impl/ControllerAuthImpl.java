package com.pryalkin.controller.impl;

import com.pryalkin.annotation.Controller;
import com.pryalkin.annotation.Url;
import com.pryalkin.controller.ControllerAuth;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.service.impl.Service;

@Controller(name = "/auth")
public class ControllerAuthImpl implements ControllerAuth {

    private final ServiceAuth serviceAuth;

    public ControllerAuthImpl(ServiceAuth serviceAuth) {
        this.serviceAuth = serviceAuth;
    }

    // service SAFETY
    @Url(name = "/registration", method = "POST")
    @Override
    public String registration(User user){
        return serviceAuth.registration(user).toString();
    }

    @Url(name = "/login", method = "POST")
    @Override
    public String getAuthorization(User user){
        return serviceAuth.getAuthorization(user);
    }

}
