package com.pryalkin.repository;

import com.pryalkin.model.User;

import java.util.List;

public interface RepositoryUser extends Repository{

    User saveUser(User user);

    List<User> findUsers();
    User findUserByUsernameAndPassword(String username, String password);
    User findUser(Integer id);


}
