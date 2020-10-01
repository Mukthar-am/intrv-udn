package com.platform.core.dao;

import com.muks.redis.RedisManager;
import com.platform.core.metadata.User;

public interface UserDao {
    public void registerUser(User user);

    public User getUserById(int userId);
}
