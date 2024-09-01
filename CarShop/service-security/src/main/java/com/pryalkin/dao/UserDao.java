package com.pryalkin.dao;

import com.pryalkin.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<List<User>> findByRole(String role);
}
