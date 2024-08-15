package com.pryalkin.controller;

import com.pryalkin.model.User;

public interface ControllerAuth extends Controller{

    String registration(User user);
    String getAuthorization(User user);
}
