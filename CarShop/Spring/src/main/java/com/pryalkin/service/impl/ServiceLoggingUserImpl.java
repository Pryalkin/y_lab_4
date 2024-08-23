//package com.pryalkin.service.impl;
//
//import com.pryalkin.model.LoggingUser;
//import com.pryalkin.model.User;
//import com.pryalkin.repository.RepositoryLoggingUser;
//import com.pryalkin.repository.RepositoryUser;
//import com.pryalkin.service.ServiceLoggingUser;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ServiceLoggingUserImpl implements ServiceLoggingUser {
//
//    private final RepositoryLoggingUser repositoryLoggingUser;
//    private final RepositoryUser repositoryUser;
//
//    public ServiceLoggingUserImpl(RepositoryLoggingUser repositoryLoggingUser,
//                                  RepositoryUser repositoryUser) {
//        this.repositoryLoggingUser = repositoryLoggingUser;
//        this.repositoryUser = repositoryUser;
//    }
//
//    @Override
//    public void saveLoggingUser(String methodname, Integer userId) {
//        User user = repositoryUser.findUser(userId);
//        LoggingUser loggingUser = new LoggingUser();
//        loggingUser.setAction("Вызов метода: " + methodname);
//        loggingUser.setDate(new LoggingUser().toString());
//        loggingUser.setUserId(user.getId());
//        repositoryLoggingUser.saveLoggingUser(loggingUser);
//    }
//}
