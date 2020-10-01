package com.muks.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisServer {
    private Config Config = new Config();
    private String RedisHost = "127.0.0.1";
    private int RedisPort = 6379;
    private boolean UseSingleServer = true;

    private static RedisServer ServerInstance = null;
    private RedissonClient RedissonClientInstance = null;


    private void RedisServer() {}


    public static RedisServer getServerInstance() {
        if (ServerInstance == null)
            ServerInstance = new RedisServer();

        return ServerInstance;
    }


    public RedisServer start() {
        if (RedissonClientInstance == null) {
            if (ServerInstance.UseSingleServer)
                ServerInstance.Config.useSingleServer();

            String instanceAddress = "redis://" + ServerInstance.RedisHost + ":" + ServerInstance.RedisPort;
            ServerInstance.Config.useSingleServer().setAddress(instanceAddress);
            /** INSTANCE.CONFIG.useClusterServers().addNodeAddress(); // for redis cluster */

            ServerInstance.RedissonClientInstance = Redisson.create(ServerInstance.Config);
        } else {
            System.out.println("Server is already running....");
        }

        return ServerInstance;
    }


    public void shutdown() {
        if (ServerInstance.RedissonClientInstance == null) {
            System.out.println("there's no running instance of server");
        } else {
            ServerInstance.RedissonClientInstance.shutdown();
        }
    }


    public RedissonClient getRedisClient() {
        return this.RedissonClientInstance;
    }
}
