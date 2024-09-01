package com.pryalkin.dao.impl;

import com.pryalkin.dao.LoggingUserDao;
import com.pryalkin.dao.SessionUtil;
import com.pryalkin.model.LoggingUser;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class LoggingUserDaoImpl implements LoggingUserDao {

    private final SessionUtil sessionUtil;

    public LoggingUserDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Optional<LoggingUser> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void save(LoggingUser loggingUser) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO logging_user (user_audit_id, action, date) values (?, ?, ?)");
            ps.setLong(1, loggingUser.getUserAudit().getId());
            ps.setString(2, loggingUser.getAction());
            ps.setString(3, loggingUser.getDate());
            ps.execute();
            sessionUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(LoggingUser loggingUser) {

    }

    @Override
    public void delete(LoggingUser loggingUser) {

    }

    @Override
    public Optional<List<LoggingUser>> findAll() {
        return Optional.empty();
    }
}
