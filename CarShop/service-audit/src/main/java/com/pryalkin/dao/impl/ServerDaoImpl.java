package com.pryalkin.dao.impl;

import com.pryalkin.dao.ServerDao;
import com.pryalkin.dao.SessionUtil;
import com.pryalkin.model.Server;
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
public class ServerDaoImpl implements ServerDao {

    private final SessionUtil sessionUtil;


    @Override
    public Optional<Server> findById(Long id) {
        sessionUtil.openConnection();
        Server server = new Server();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM servers WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                server.setId(rs.getLong(1));
                server.setServername(rs.getString(2));
                server.setServerpassword(rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(server.getId()) ? Optional.empty() : Optional.of(server);
    }

    @Override
    public void save(Server server) {
        sessionUtil.openConnection();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("INSERT INTO servers (servername, serverpassword, token) values (?, ?, ?)");
            ps.setString(1, server.getServername());
            ps.setString(2, server.getServerpassword());
            ps.setString(3, server.getToken());
            ps.execute();
            sessionUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void update(Server server) {
        sessionUtil.openConnection();
        try {
            if(findById(server.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection().prepareStatement("UPDATE servers SET servername = ?, password = ? WHERE id = ?");
                ps.setString(1, server.getServername());
                ps.setString(2, server.getServerpassword());
                ps.setLong(3, server.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public void delete(Server server) {
        sessionUtil.openConnection();
        try {
            if(findById(server.getId()).isPresent()){
                PreparedStatement ps = sessionUtil.getConnection().prepareStatement("DELETE FROM servers WHERE id = ?");
                ps.setLong(1, server.getId());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
    }

    @Override
    public Optional<List<Server>> findAll() {
        sessionUtil.openConnection();
        List<Server> servers = new ArrayList<>();
        try (PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM servers")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Server server = new Server();
                server.setId(rs.getLong(1));
                server.setServername(rs.getString(2));
                server.setServerpassword(rs.getString(3));
                servers.add(server);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return servers.isEmpty() ? Optional.empty() : Optional.of(servers);
    }

    @Override
    public Optional<Server> findByServername(String servername) {
        sessionUtil.openConnection();
        Server server = new Server();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM servers WHERE servername = ?");
            ps.setString(1, servername);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                server.setId(rs.getLong(1));
                server.setServername(rs.getString(2));
                server.setServerpassword(rs.getString(3));
                server.setToken(rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(server.getId()) ? Optional.empty() : Optional.of(server);
    }

    @Override
    public Optional<Server> findByServernameAndServerpassword(String servername, String serverpassword) {
        sessionUtil.openConnection();
        Server server = new Server();
        try {
            PreparedStatement ps = sessionUtil.getConnection().prepareStatement("SELECT * FROM servers WHERE servername = ? AND serverpassword = ?");
            ps.setString(1, servername);
            ps.setString(2, serverpassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                server.setId(rs.getLong(1));
                server.setServername(rs.getString(2));
                server.setServerpassword(rs.getString(3));
                server.setToken(rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sessionUtil.closeConnection();
        return Objects.isNull(server.getId()) ? Optional.empty() : Optional.of(server);
    }
}
