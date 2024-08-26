package com.pryalkin.service;

import com.pryalkin.model.User;

public interface ServiceLoggingUser {

    void saveLoggingUser(String methodname, User user);
}
