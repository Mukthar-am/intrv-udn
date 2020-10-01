package com.platform.services.metadata;

import com.muks.redis.RedisManager;
import com.platform.core.metadata.User;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

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

    @NotBlank
    @Length(min = 1, max = 120)
    private String DeviceId = new String("");

    private List<Integer> UserDevices = new ArrayList<>();

    public PlatformUser() {
    }

    public PlatformUser(int id,
                        String name,
                        String age,
                        String gender,
                        String phoneNumber,
                        int deviceId) {

        this.Id = id;
        this.Name = name;
        this.Age = age;
        this.Gender = gender;
        this.PhoneNumber = phoneNumber;

        /** NotBlack validation is already in place */
        this.UserDevices.add(deviceId);
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

    public String getDeviceId() {
        return DeviceId;
    }

    public void setdeviceid(String deviceId) {
        DeviceId = deviceId;
    }

    // event_date, source, trip_type, pickup_time, pickup_location, pickup_dropoff
    public String toString() {
        return "{name:" + getname() + "," +
                "id:" + this.getId() + "," +
                "gender:" + this.getGender() + "," +
                "phNo:" + this.getPhoneNumber() + "," +
                "age:" + this.getAge() + "," +
                "deviceId:" + this.getDeviceId() +
                "}";
    }

    @Override
    public void registerUser() {
        RedisManager.getInstance().startServer().getNameSpace(this.NameSpace)
                .put(getId(), this);
    }

    @Override
    public PlatformUser getUserById() {
        return (PlatformUser) RedisManager.getInstance().getNameSpace(this.NameSpace).get(this.Id);
    }


}
