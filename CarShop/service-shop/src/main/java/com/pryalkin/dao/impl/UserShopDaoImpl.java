package com.pryalkin.dao.impl;

import com.pryalkin.dao.SessionUtil;
import com.pryalkin.dao.UserShopDao;
import com.pryalkin.model.UserShop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserShopDaoImpl implements UserShopDao {

    private final SessionUtil sessionUtil;


    @Override
    public Optional<UserShop> findById(Long id) {
        sessionUtil.openConnection();
        UserShop user = new UserShop();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM user_shop WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getLong(1));
                user.setEmail(rs.getString(2));
                user.setName(rs.getString(3));
                user.setSurname(rs.getString(4));
                user.setRole(rs.getString(5));
                String[] auths = rs.getString(6).split(",");
                user.setAuthorities(auths);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(user.getId()) ? Optional.empty() : Optional.of(user);
    }

    @Override
    public void save(UserShop userShop) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO user_shop (id, email, name, surname, role, authorities) values (?, ?, ?, ?, ?, ?)");
            ps.setLong(1, userShop.getId());
            ps.setString(2, userShop.getEmail());
            ps.setString(3, userShop.getName());
            ps.setString(4, userShop.getSurname());
            ps.setString(5, userShop.getRole());
            ps.setString(6, String.join(",", userShop.getAuthorities()));
            ps.execute();
            sessionUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(UserShop userShop) {
        sessionUtil.openConnection();
        try {
            if(findById(userShop.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection()
                        .prepareStatement("UPDATE user_shop SET email = ?, name = ?, surname = ?, role = ?,  authorities = ? WHERE id = ?");
                ps.setString(1, userShop.getEmail());
                ps.setString(2, userShop.getName());
                ps.setString(3, userShop.getSurname());
                ps.setString(4, userShop.getRole());
                ps.setString(5, String.join(",", userShop.getAuthorities()));
                ps.setLong(6, userShop.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void delete(UserShop userShop) {
        sessionUtil.openConnection();
        try {
            if(findById(userShop.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection().prepareStatement("DELETE FROM user_shop WHERE id = ?");
                ps.setLong(1, userShop.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public Optional<List<UserShop>> findAll() {
        sessionUtil.openConnection();
        List<UserShop> userShops = new ArrayList<>();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM user_shop");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UserShop userShop = new UserShop();
                userShop.setId(rs.getLong(1));
                userShop.setEmail(rs.getString(2));
                userShop.setName(rs.getString(3));
                userShop.setSurname(rs.getString(4));
                userShop.setRole(rs.getString(5));
                String[] auths = rs.getString(6).split(",");
                userShop.setAuthorities(auths);
                userShops.add(userShop);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return userShops.isEmpty() ? Optional.empty() : Optional.of(userShops);
    }

}
