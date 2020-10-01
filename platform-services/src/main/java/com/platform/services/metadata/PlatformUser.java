package com.platform.services.metadata;

import com.muks.redis.RedisManager;
import com.platform.core.metadata.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class PlatformUser implements PlatformUserDao {
    public static final String NameSpace = "users";

    @Length(min = 4, max = 20)
    private String Name = "";

    @Length(min = 1, max = 3)
    private String Age = "";

    private int Id = -1;

    @NotBlank
    @Length(min = 1, max = 5)
    private String Gender = new String("");

    @NotBlank
    @Length(min = 10, max = 12)
    private String PhoneNumber = new String("");

    public PlatformUser() {
    }

    public PlatformUser(int id,
                        String name,
                        String age,
                        String gender,
                        String phoneNumber) {

        this.Id = id;
        this.Name = name;
        this.Age = age;
        this.Gender = gender;
        this.PhoneNumber = phoneNumber;
    }


    public void setid(int id) {
        this.Id = id;
    }

    public int getId() {
        return this.Id;
    }

    public String getname() {
        return Name;
    }

    public void setname(String name) {
        this.Name = name;
    }

    public void setage(String age) {
        this.Age = age;
    }

    public String getAge() {
        return this.Age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    // event_date, source, trip_type, pickup_time, pickup_location, pickup_dropoff
    public String toString() {
        return "{name:" + getname() + "," +
                "id:" + this.getId() + "," +
                "gender:" + this.getGender() + "," +
                "phNo:" + this.getPhoneNumber() + "," +
                "age:" + this.getAge() +
                "}";
    }

    @Override
    public void registerUser() {
        RedisManager.getInstance().startServer().getNameSpace(User.NameSpace)
                .put(getId(), this);
    }

    @Override
    public PlatformUser getUserById() {
        return (PlatformUser) RedisManager.getInstance().getNameSpace(User.NameSpace).get(this.Id);
    }


}
