package com.pryalkin.service;

import com.pryalkin.model.User;

import java.util.Set;

public interface ServiceUser extends Service{

    Set<User> getRegistrationClient();

    User getUser(User user);

    Set<User> getRegistrationManager();

}
