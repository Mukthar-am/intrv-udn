package com.platform.core.impl;

import com.muks.redis.RedisManager;
import com.platform.core.dao.UserDao;
import com.platform.core.metadata.User;

public class UserDaoImpl implements UserDao {

    @Override
    public void registerUser(User user) {
        RedisManager.getInstance().startServer().getNameSpace(User.NameSpace).put(user.getId(), user);
    }

    @Override
    public User getUserById(int userId) {
        return (User) RedisManager.getInstance().getNameSpace(User.NameSpace).get(userId);
    }
}
