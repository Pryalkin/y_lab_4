package com.pryalkin.service.impl;

import com.pryalkin.dao.LoggingUserDao;
import com.pryalkin.dao.UserDao;
import com.pryalkin.model.LoggingUser;
import com.pryalkin.model.User;

import com.pryalkin.service.ServiceLoggingUser;
import org.springframework.stereotype.Service;

@Service
public class ServiceLoggingUserImpl implements ServiceLoggingUser {

    private final LoggingUserDao loggingUserDao;
    private final UserDao userDao;

    public ServiceLoggingUserImpl(LoggingUserDao loggingUserDao,
                                  UserDao userDao) {
        this.loggingUserDao = loggingUserDao;
        this.userDao = userDao;
    }

    @Override
    public void saveLoggingUser(String methodname, User user) {
        LoggingUser loggingUser = new LoggingUser();
        loggingUser.setAction("Вызов метода: " + methodname);
        loggingUser.setDate(new LoggingUser().toString());
        loggingUser.setUser(user);
        loggingUserDao.save(loggingUser);
    }
}
