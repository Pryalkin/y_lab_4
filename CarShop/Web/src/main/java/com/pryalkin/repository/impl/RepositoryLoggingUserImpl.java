package com.pryalkin.repository.impl;

import com.pryalkin.model.LoggingUser;
import com.pryalkin.repository.RepositoryLoggingUser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class RepositoryLoggingUserImpl implements RepositoryLoggingUser {

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

    @Override
    public void saveLoggingUser(LoggingUser loggingUser) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO logging_users (userId, action, date) values (?, ?, ?)")){
            ps.setInt(1, loggingUser.getUserId());
            ps.setString(2, loggingUser.getAction());
            ps.setString(3, loggingUser.getDate());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
