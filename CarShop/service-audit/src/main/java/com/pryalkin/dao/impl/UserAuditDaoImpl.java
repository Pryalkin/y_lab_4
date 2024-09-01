package com.pryalkin.dao.impl;

import com.pryalkin.dao.SessionUtil;
import com.pryalkin.dao.UserAuditDao;
import com.pryalkin.model.UserAudit;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserAuditDaoImpl implements UserAuditDao {

    private final SessionUtil sessionUtil;

    public UserAuditDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Optional<UserAudit> findById(Long id) {
        sessionUtil.openConnection();
        UserAudit user = new UserAudit();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM user_audit WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(user.getId()) ? Optional.empty() : Optional.of(user);
    }

    @Override
    public void save(UserAudit userAudit) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO user_audit (id, name, surname) values (?, ?, ?)");
            ps.setLong(1, userAudit.getId());
            ps.setString(2, userAudit.getName());
            ps.setString(3, userAudit.getSurname());
            ps.execute();
            sessionUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(UserAudit userAudit) {

    }

    @Override
    public void delete(UserAudit userAudit) {

    }

    @Override
    public Optional<List<UserAudit>> findAll() {
        return Optional.empty();
    }
}
