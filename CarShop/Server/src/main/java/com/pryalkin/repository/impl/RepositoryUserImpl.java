package com.pryalkin.repository.impl;

import com.pryalkin.annotation.Repository;
import com.pryalkin.model.User;
import com.pryalkin.repository.RepositoryUser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class RepositoryUserImpl implements RepositoryUser {

    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = RepositoryUserImpl.class
                .getClassLoader().getResourceAsStream("app.properties")){
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private Long idUser = 0L;
//    private Map<Long, User> users = new HashMap<>();

    @Override
    public User saveUser(User user){
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, surname, username, password, role) values (?, ?, ?, ?, ?)")){
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public List<User> findUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(rs.getString(6));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = new User();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?" )){
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(rs.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User findUser(Integer id) {
        User user = new User();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?" )){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(rs.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
