package com.platform.core;

import com.muks.redis.RedisManager;
import com.platform.core.impl.UserDaoImpl;
import com.platform.core.metadata.User;
import org.redisson.api.RedissonClient;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestsUser {
    private RedisManager CacheManager = null;//new RedisManager().startServer()
//    private RedissonClient CacheClient = null;


    @BeforeTest
    public void initCache() {
        CacheManager = RedisManager.getInstance().startServer();
//        CacheClient = CacheManager.getRedisClient();
    }

    @AfterTest
    public void killCache() {
        CacheManager.stopServer();
    }

    @Test
    public void TestUserRegistration() {
        User user1 = new User(1);
        user1.setName("Mukthar");
        user1.setAge(38);
        user1.setGender("M");

        User user2 = new User(2);
        user2.setName("Ahmed");
        user2.setAge(31);
        user2.setGender("M");

        try {
            /** user registration */
            UserDaoImpl userDaoImpl = new UserDaoImpl();
            userDaoImpl.registerUser(user1);
            userDaoImpl.registerUser(user2);

            Assert.assertEquals(
                    CacheManager.getNameSpace(user1.getNameSpace()).get(user1.getId()).toString(),
                    "id=1,name=Mukthar,age=38,gender=M,ph="
            );

            Assert.assertEquals(
                    CacheManager.getNameSpace(user2.getNameSpace()).get(user2.getId()).toString(),
                    "id=2,name=Ahmed,age=31,gender=M,ph="
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void TestUserCacheQuery() {
        User user1 = new User(1);
        user1.setName("Mukthar");
        user1.setAge(38);
        user1.setGender("M");

        User user2 = new User(2);
        user2.setName("Ahmed");
        user2.setAge(31);
        user2.setGender("M");

        /** user registration */
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.registerUser(user1);
        userDaoImpl.registerUser(user2);


        try {
            User queriedUser1 = (User) CacheManager.queryNameSpace(user1.getNameSpace(), user1.getId());
            User queriedUser2 = (User) CacheManager.queryNameSpace(user1.getNameSpace(), user2.getId());

            Assert.assertEquals(
                    queriedUser1.toString(),
                    "id=1,name=Mukthar,age=38,gender=M,ph="
            );

            Assert.assertEquals(
                    queriedUser2.toString(),
                    "id=2,name=Ahmed,age=31,gender=M,ph="
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TestUserChangingSchema() {
        User user1 = new User(1);
        user1.setName("Mukthar");
        user1.setAge(38);

        User user2 = new User(2);
        user2.setName("Ahmed");
        user2.setGender("M");
        user2.setAge(31);

        User user3 = new User(3);
        user3.setName("muks");
        user3.setGender("M");
        user3.setAge(31);
        user3.setPhoneNumber("+919886653306");

        User user4 = new User(4);
        user4.setName("Ahmed");
        user4.setGender("M");
        user4.setAge(11);

        /** user registration */
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.registerUser(user1);
        userDaoImpl.registerUser(user2);
        userDaoImpl.registerUser(user3);
        userDaoImpl.registerUser(user4);

        try {

            User queriedUser3 = (User) CacheManager.queryNameSpace(User.NameSpace, user3.getId());
            User queriedUser4 = (User) CacheManager.queryNameSpace(User.NameSpace, user4.getId());

            User queriedUser1 = (User) CacheManager.queryNameSpace(User.NameSpace, user1.getId());
            User queriedUser2 = (User) CacheManager.queryNameSpace(User.NameSpace, user2.getId());

            Assert.assertEquals(
                    queriedUser1.toString(),
                    "id=1,name=Mukthar,age=38,gender=,ph="
            );

            Assert.assertEquals(
                    queriedUser2.toString(),
                    "id=2,name=Ahmed,age=31,gender=M,ph="
            );

            Assert.assertEquals(
                    queriedUser3.toString(),
                    "id=3,name=muks,age=31,gender=M,ph=+919886653306"
            );

            Assert.assertEquals(
                    queriedUser4.toString(),
                    "id=4,name=Ahmed,age=11,gender=M,ph="
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void TestUserById() {
        User user1 = new User(1);
        user1.setName("Mukthar");
        user1.setAge(38);
        user1.setGender("M");

        User user2 = new User(2);
        user2.setName("Ahmed");
        user2.setAge(31);
        user2.setGender("M");

        /** user registration */
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.registerUser(user1);
        userDaoImpl.registerUser(user2);

        System.out.println("user-2: " + userDaoImpl.getUserById(user2.getId()));
        try {
            User queriedUser1 = userDaoImpl.getUserById(user1.getId());
            User queriedUser2 = userDaoImpl.getUserById(user2.getId());

            Assert.assertEquals(
                    queriedUser1.toString(),
                    "id=1,name=Mukthar,age=38,gender=M,ph="
            );

            Assert.assertEquals(
                    queriedUser2.toString(),
                    "id=2,name=Ahmed,age=31,gender=M,ph="
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
