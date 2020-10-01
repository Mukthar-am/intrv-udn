package com.platform.services.platform;

import com.muks.redis.RedisManager;
import com.platform.core.metadata.User;
import com.platform.services.metadata.PlatformDevice;
import com.platform.services.metadata.PlatformUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlatformUserFunctions {
    final static Logger logger = LoggerFactory.getLogger(PlatformUserFunctions.class);

    public static PlatformUser isKnownUser(PlatformUser user) {
        logger.info("checking is user is known to us...: " + user.getId());
        return (PlatformUser) RedisManager.getInstance().getNameSpace(user.NameSpace).get(user.getId());
    }

    public static PlatformDevice isKnownDevice(PlatformDevice device) {
        logger.info("checking is getDeviceId is known to us...: " + device.getDeviceId());
        return (PlatformDevice) RedisManager.getInstance().getNameSpace(device.NameSpace).get(device.getDeviceId());
    }
}
