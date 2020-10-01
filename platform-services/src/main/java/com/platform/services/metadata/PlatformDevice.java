package com.platform.services.metadata;

import com.muks.redis.RedisManager;
import com.platform.services.services.RegistrationService;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PlatformDevice {
    final static Logger logger = LoggerFactory.getLogger(PlatformDevice.class);
    public static final String NameSpace = "devices";

    private int UserId = -1;

    @Length(min = 4, max = 20)
    private String Name = "";

    @NotNull(message = "DeviceId must be a valid positive integer")
    private int DeviceId = 1;

    private List<Integer> Capabilities = new ArrayList<>();

    private int Capability = -1;

    public PlatformDevice() {
    }

    public PlatformDevice(int userId,
                          String name,
                          int deviceId,
                          int capability) {

        this.UserId = userId;
        this.Name = name;
        this.DeviceId = deviceId;

        /** NotBlack validation is already in place */
        this.Capabilities.add(capability);

    }

    public void setuserid(int id) {
        this.UserId = id;
    }

    public int getUserId() {
        return this.UserId;
    }

    public String getname() {
        return Name;
    }

    public void setname(String name) {
        this.Name = name;
    }


    public int getDeviceId() {
        return DeviceId;
    }

    public void setdeviceid(int deviceId) {
        this.DeviceId = deviceId;
    }

    public List<Integer> getCapabilities() {
        return Capabilities;
    }

    public void setCapabilities(List<Integer> capabilities) {
        Capabilities = capabilities;
    }

    public int getCapability() {
        return Capability;
    }

    public void setcapability(int capability) {
        Capability = capability;
    }

    // event_date, source, trip_type, pickup_time, pickup_location, pickup_dropoff
    public String toString() {
        return "{userid:" + getDeviceId() + "," +
                "name:" + getname() + "," +
                "deviceId:" + this.getDeviceId() + "," +
                "capabilities:" + this.getCapabilities().toString() +
                "}";
    }


    public void registerDevice() {
        RedisManager.getInstance().startServer().getNameSpace(this.NameSpace).put(this.DeviceId, this);
    }

    public void registerDeviceToUser() {
        int userId = this.UserId;

        PlatformUser userMap = (PlatformUser) RedisManager.getInstance().startServer()
                .getNameSpace(PlatformUser.NameSpace)
                .get(userId);

        userMap.addDevice(this.DeviceId);

        RedisManager.getInstance().startServer()
                .getNameSpace(PlatformUser.NameSpace)
                .put(userId, userMap);

        logger.info("userMap: " + userMap.getDeviceId() + ", " + userMap.getId());

    }


    public PlatformDevice getDeviceById() {
        return (PlatformDevice) RedisManager.getInstance().getNameSpace(this.NameSpace).get(this.DeviceId);
    }


}
