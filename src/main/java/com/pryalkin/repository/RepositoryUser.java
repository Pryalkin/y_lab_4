package com.pryalkin.repository;

import com.pryalkin.model.User;

import java.util.Collection;

public interface RepositoryUser extends Repository{

    User saveUser(User user);

    Collection<User> findUsers();
    User findUserByUsernameAndPassword(String username, String password);
    User findUser(Long id);


}
