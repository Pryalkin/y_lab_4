package com.pryalkin.service;

import com.pryalkin.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ServiceUser extends Service{

    Set<User> getRegistrationClient();

    User getUser(User user);

    Set<User> getRegistrationManager();

}
