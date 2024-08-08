package com.pryalkin.repository.impl;

import com.pryalkin.annotation.Repository;
import com.pryalkin.model.User;
import com.pryalkin.repository.RepositoryUser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryUserImpl implements RepositoryUser {

    private Long idUser = 0L;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User saveUser(User user) {
        idUser++;
        user.setId(idUser.toString());
        users.put(idUser, user);
        return user;
    }


    @Override
    public Collection<User> findUsers() {
        return users.values();
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        List<User> userList = users.values().stream()
                .filter(user -> user.getUsername().equals(username)
                        && user.getPassword().equals(password)).toList();
        if(userList.isEmpty()) return null;
        return userList.get(0);
    }

    @Override
    public User findUser(Long id) {
        return users.get(id);
    }
}
