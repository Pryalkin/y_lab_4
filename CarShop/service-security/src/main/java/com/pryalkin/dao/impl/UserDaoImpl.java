package com.pryalkin.dao.impl;

import com.pryalkin.dao.SessionUtil;
import com.pryalkin.dao.UserDao;
import com.pryalkin.model.Server;
import com.pryalkin.model.User;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {


    private final SessionUtil sessionUtil;

    public UserDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Optional<User> findById(Long id) {
        sessionUtil.openConnection();
        User user = new User();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getLong(1));
                user.setEmail(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                user.setRole(rs.getString(6));
                String[] auths = rs.getString(7).split(",");
                user.setAuthorities(auths);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(user.getId()) ? Optional.empty() : Optional.of(user);
    }

    @Override
    public void save(User user) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO users (email, password, name, surname, role, authorities) values (?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurname());
            ps.setString(5, user.getRole());
            ps.setString(6, String.join(",", user.getAuthorities()));
            ps.execute();
            sessionUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(User user) {
        sessionUtil.openConnection();
        try {
            if(findById(user.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection()
                        .prepareStatement("UPDATE users SET email = ?, password = ?, name = ?, surname = ?, role = ?,  authorities = ? WHERE id = ?");
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getName());
                ps.setString(4, user.getSurname());
                ps.setString(5, user.getRole());
                ps.setString(6, String.join(",", user.getAuthorities()));
                ps.setLong(7, user.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void delete(User user) {
        sessionUtil.openConnection();
        try {
            if(findById(user.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection().prepareStatement("DELETE FROM users WHERE id = ?");
                ps.setLong(1, user.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public Optional<List<User>> findAll() {
        sessionUtil.openConnection();
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong(1));
                user.setEmail(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                user.setRole(rs.getString(6));
                String[] auths = rs.getString(7).split(",");
                user.setAuthorities(auths);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        sessionUtil.openConnection();
        User user = new User();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong(1));
                user.setEmail(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                user.setRole(rs.getString(6));
                String[] auths = rs.getString(7).split(",");
                user.setAuthorities(auths);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(user.getId()) ? Optional.empty() : Optional.of(user);
    }

    @Override
    public Optional<List<User>> findByRole(String role) {
        sessionUtil.openConnection();
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM users WHERE role = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong(1));
                user.setEmail(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                user.setRole(rs.getString(6));
                String[] auths = rs.getString(7).split(",");
                user.setAuthorities(auths);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }
}
