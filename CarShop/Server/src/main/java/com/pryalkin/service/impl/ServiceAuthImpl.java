package com.pryalkin.service.impl;

import com.pryalkin.annotation.Service;
import com.pryalkin.model.User;
import com.pryalkin.repository.RepositoryUser;
import com.pryalkin.service.ServiceAuth;

@Service
public class ServiceAuthImpl implements ServiceAuth {

    private final RepositoryUser repositoryUser;

    public ServiceAuthImpl(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Override
    public User registration(User user) {
        return repositoryUser.saveUser(user);
    }

    @Override
    public String getAuthorization(User user) {
        boolean authorization = repositoryUser.findUsers().stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername())
                        && u.getPassword().equals(user.getPassword()));
        if (authorization){
            User generateTokenForUser = this.getUser(user);
            return encrypt(generateTokenForUser.getUsername() + "." + generateTokenForUser.getPassword(), 1);
        }
        return Boolean.FALSE.toString();
    }

    @Override
    public User getUser(User user) {
        return repositoryUser.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                boolean isUpperCase = Character.isUpperCase(ch);
                char base = isUpperCase ? 'A' : 'a';
                int shiftedIndex = (ch - base + shift) % 26;
                result.append((char) (base + shiftedIndex));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String encryptedText, int shift) {
        return encrypt(encryptedText, -shift);
    }

    @Override
    public boolean checkToken(String authorities) {
        String usernameAndPassword = decrypt(authorities, 1);
        int index = usernameAndPassword.indexOf('.');
        String username = usernameAndPassword.substring(0, index);
        String password = usernameAndPassword.substring(index+1);
        User user = repositoryUser.findUserByUsernameAndPassword(username, password);
        return user == null;
    }
}
