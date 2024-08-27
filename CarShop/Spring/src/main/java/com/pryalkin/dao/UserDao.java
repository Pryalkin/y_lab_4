package com.pryalkin.dao;

import com.pryalkin.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User, Long>{

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<List<User>> findByRole(String name);
}
