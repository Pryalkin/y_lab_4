package com.pryalkin.dao;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Component
public class SessionUtil {

    private Connection conn;
    private String url;
    private String username;
    private String password;

    public void openConnection(){
        try(InputStream in = SessionUtil.class
                .getClassLoader().getResourceAsStream("application.yml")){
            Yaml yaml = new Yaml();
            Map<String, Map<String, Map<String, String>>> data = yaml.load(in);
            url = data.get("spring").get("datasource").get("url");
            username = data.get("spring").get("datasource").get("username");
            password = data.get("spring").get("datasource").get("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public Connection getConnection(){
        return conn;
    }

    public void closeConnection(){
        try {
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Ошибка: Транзакция отменена.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            url = null;
            username = null;
            password = null;
            try {
                if(conn != null){
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
