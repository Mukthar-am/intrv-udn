package com.platform.services.metadata;

import com.muks.redis.RedisManager;
import com.platform.core.metadata.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PlatformDevice {
    public static final String NameSpace = "devices";

    @Length(min = 4, max = 20)
    private String Name = "";

    @NotNull(message = "DeviceId must be a valid positive integer")
    private int DeviceId = 1;

    private List<Integer> Capabilities = new ArrayList<>();

    private int Capability = -1;

    public PlatformDevice() {
    }

    public PlatformDevice(String name,
                          int deviceId,
                          int capability) {

        this.Name = name;
        this.DeviceId = deviceId;

        /** NotBlack validation is already in place */
        this.Capabilities.add(capability);

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
        return "{name:" + getname() + "," +
                "deviceId:" + this.getDeviceId() + "," +
                "capabilities:" + this.getCapabilities().toString() +
                "}";
    }


    public void registerUser() {
        RedisManager.getInstance().startServer().getNameSpace(this.NameSpace).put(this.DeviceId, this);
    }


    public PlatformDevice getUserById() {
        return (PlatformDevice) RedisManager.getInstance().getNameSpace(this.NameSpace).get(this.DeviceId);
    }


}
