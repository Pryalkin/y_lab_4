package com.pryalkin.service;

import com.pryalkin.model.User;

public interface ServiceAuth extends Service {

    User registration(User user);

    String getAuthorization(User user);
    User getUser(User user);
    String encrypt(String text, int shift);

    String decrypt(String encryptedText, int shift);
    boolean checkToken(String authorities);
}
