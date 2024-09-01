package com.pryalkin.service.impl;

import com.pryalkin.dao.UserShopDao;
import com.pryalkin.model.UserShop;
import com.pryalkin.service.UserShopService;

public class UserShopServiceImpl implements UserShopService {

    private UserShopDao userShopDao;

    public UserShopServiceImpl(UserShopDao userShopDao) {
        this.userShopDao = userShopDao;
    }

    @Override
    public UserShop getUserShop(Long userId) {
        UserShop userShop = userShopDao.findById(userId)
                .orElseThrow();
        return null;
    }
}
