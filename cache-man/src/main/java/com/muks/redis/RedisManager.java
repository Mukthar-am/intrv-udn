package com.muks.redis;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.io.IOException;

public class RedisManager {
    private RedissonClient RedisClient = null;
    private static RedisManager Instance = null;

    private RedisManager() {
    }

    public static RedisManager getInstance() {
        if (Instance == null)
            Instance = new RedisManager();

        return Instance;
    }

    public RedisManager startServer() {
        if (RedisClient == null) {
            RedisClient = init();
        }

        return Instance;
    }


    public void stopServer() {
        if (RedisServer.getServerInstance() != null)
            RedisServer.getServerInstance().shutdown();
    }


    public RedissonClient getRedisClient() {
        if (this.RedisClient == null) {
            this.RedisClient = init();
        }

        return RedisClient;
    }


    public RMap<Object, Object> createNameSpace(String name) {
        return this.RedisClient.getMap(name);
    }


    public RMap<Object, Object> getNameSpace(String name) {
        return this.RedisClient.getMap(name);
    }


    private RedissonClient init() {
        return RedisServer.getServerInstance().start().getRedisClient();
    }


    public Object queryNameSpace(String nameSpace, Object key) throws Exception {
        if (isNameSpaceEmpty(nameSpace)) {
            throw new Exception("ERROR: namespace empty");
        }

        return RedisClient.getMap(nameSpace).get(key);
    }


    public boolean isNameSpaceEmpty(String nameSpace) {
        if (RedisClient == null) {
            System.out.println("RedisClient is null");
        }
        return RedisClient.getMap(nameSpace).isEmpty();
    }


    public void updateNameSpace(String nameSpace, Object key) throws Exception {
        if (isNameSpaceEmpty(nameSpace)) {
            throw new Exception("ERROR: namespace empty");
        }

//        return RedisClient.getMap(nameSpace).put(key.g);
    }


    public static void main(String[] args) throws IOException {

        RedisManager redisManager = new RedisManager().startServer();

        RMap user = redisManager.createNameSpace("user");
        user.put("muks", "78");

        System.out.println("user: " + user.get("muks"));


        redisManager.stopServer();


//
//        RMap map = redisClient.getMap("myMap");
//        map.put("a", 1);
//        map.put("b", 2);
//        map.put("c", 3334);
//
//        boolean contains = map.containsKey("a");
//        System.out.println("Contains \"a\" - " + contains);
//
//        Integer value = (Integer) map.get("c");
//        System.out.println("value=" + value);
//
//        Integer updatedValue = (Integer) map.addAndGet("a", 32);
//        System.out.println("updatedValue=" + updatedValue);
//
//
//        Set keys = new HashSet();
//        keys.add("a");
//        keys.add("b");
//        keys.add("c");
//        Map mapSlice = map.getAll(keys);
//        System.out.println("all keys: " + mapSlice.toString());

//        // use read* methods to fetch all objects
//        Set allKeys = map.readAllKeySet();
//        System.out.println("all keys: " + mapSlice.toString());
//
//        Collection allValues = map.readAllValues();
//        System.out.println("all keys: " + mapSlice.toString());
//
//        Set allEntries = map.readAllEntrySet();
//        System.out.println("allEntries: " + allEntries.toString());
//
//        // use fast* methods when previous value is not required
//        boolean isNewKey = map.fastPut("aa", 100);
//        System.out.println("isNewKey: " + isNewKey);
//
//        boolean isNewKeyPut = map.fastPutIfAbsent("d", 33);
//        System.out.println("isNewKeyPut: " + isNewKeyPut);
//
//        long removedAmount = map.fastRemove("b");
//        System.out.println("removedAmount: " + removedAmount);

//        redisClient.shutdown();
    }

}

