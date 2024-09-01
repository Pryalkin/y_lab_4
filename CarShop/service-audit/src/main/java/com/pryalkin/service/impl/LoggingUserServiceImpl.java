package com.pryalkin.service.impl;

import com.pryalkin.dao.LoggingUserDao;
import com.pryalkin.dao.UserAuditDao;
import com.pryalkin.model.LoggingUser;
import com.pryalkin.model.UserAudit;
import com.pryalkin.service.LoggingUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoggingUserServiceImpl implements LoggingUserService {

    private final LoggingUserDao loggingUserDao;
    private final UserAuditDao userAuditDao;

    @Override
    public void send(LoggingUser loggingUser) {
        UserAudit userAudit = userAuditDao.findById(loggingUser.getUserAudit().getId())
                .orElseGet(() -> {
                    userAuditDao.save(loggingUser.getUserAudit());
                    return userAuditDao.findById(loggingUser.getId()).get();
                });
        loggingUser.setUserAudit(userAudit);
        loggingUserDao.save(loggingUser);
    }
}
