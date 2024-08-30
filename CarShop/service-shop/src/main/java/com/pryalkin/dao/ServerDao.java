package com.pryalkin.dao;

import com.pryalkin.model.Server;

import java.util.Optional;

public interface ServerDao extends Dao<Server, Long>{

    Optional<Server> findByServername(String servername);

    Optional<Server> findByServernameAndServerpassword(String servername, String serverpassword);
}
