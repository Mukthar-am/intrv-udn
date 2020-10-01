package com.platform.services.metadata;

import com.muks.redis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

public class Metrices {
    final static Logger logger = LoggerFactory.getLogger(Metrices.class);
    public static final String NameSpace = "metrices";

    /**
     * height, weight, heart-rate, calories-consumed, calories-burnt
     */

    @NotNull
    private int UserId = -1;

    @NotNull
    private int DeviceId = -1;

    private int Height = -1;

    private int Weight = -1;

    private int HeartRate = -1;

    private int CaloriesConsumed = -1;

    private int CaloriesBurnt = -1;

    public Metrices() {
    }


    public Metrices(int userId,
                    int deviceId,
                    int height,
                    int weight,
                    int heartRate,
                    int caloriesConsumed,
                    int caloriesBurnt) {

        this.UserId = userId;
        this.DeviceId = deviceId;
        this.Height = height;
        this.Weight = weight;
        this.HeartRate = heartRate;
        this.CaloriesConsumed = caloriesConsumed;
        this.CaloriesBurnt = caloriesBurnt;
    }

    public int getUserId() {
        return UserId;
    }

    public void setuserid(int userId) {
        UserId = userId;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public void setdeviceid(int deviceId) {
        DeviceId = deviceId;
    }

    public int getHeight() {
        return Height;
    }

    public void setheight(int height) {
        Height = height;
    }

    public int getWeight() {
        return Weight;
    }

    public void setweight(int weight) {
        Weight = weight;
    }

    public int getHeartRate() {
        return HeartRate;
    }

    public void setheartrate(int heartRate) {
        HeartRate = heartRate;
    }

    public int getCaloriesConsumed() {
        return CaloriesConsumed;
    }

    public void setcaloriesconsumed(int caloriesConsumed) {
        CaloriesConsumed = caloriesConsumed;
    }

    public int getCaloriesBurnt() {
        return CaloriesBurnt;
    }

    public void setcaloriesburnt(int caloriesBurnt) {
        CaloriesBurnt = caloriesBurnt;
    }

    // event_date, source, trip_type, pickup_time, pickup_location, pickup_dropoff
    public String toString() {
        StringBuilder metricCollector = new StringBuilder("{");


        metricCollector.append("userid: " + getUserId());

        metricCollector.append(",deviceid: " + getDeviceId());


        if (this.getHeight() != -1) {
            metricCollector.append(",height: " + getHeight());
        }

        if (this.getWeight() != -1) {
            metricCollector.append(",weight: " + getWeight());
        }

        if (this.getHeartRate() != -1) {
            metricCollector.append(",heart-rate: " + getHeartRate());
        }

        if (this.getCaloriesConsumed() != -1) {
            metricCollector.append(",calConsumed: " + getCaloriesConsumed());
        }

        if (this.getCaloriesBurnt() != -1) {
            metricCollector.append(",calBurnt: " + getCaloriesBurnt());
        }

        return metricCollector.append("}").toString();
    }


    public void addMetrices() {
        PlatformUser userMap = (PlatformUser) RedisManager.getInstance().startServer()
                .getNameSpace(PlatformUser.NameSpace)
                .get(this.UserId);

        long currentTimestamp = System.currentTimeMillis();

        if (getCaloriesBurnt() != -1)
            userMap.addCaloriesBurnt(currentTimestamp, getCaloriesBurnt());

        if (getCaloriesConsumed() != -1)
            userMap.addCaloriesConsumed(currentTimestamp, getCaloriesConsumed());

        if (getHeartRate() != -1)
            userMap.addHeartRate(currentTimestamp, getHeartRate());

        if (getHeight() != -1)
            userMap.setHeight(getHeight());

        if (getWeight() != -1)
            userMap.setWeight(getWeight());

        RedisManager.getInstance().startServer()
                .getNameSpace(PlatformUser.NameSpace)
                .put(this.UserId, userMap);

        logger.info("userMap: " + userMap.toString());

    }


    public Metrices getUserById() {
        return (Metrices) RedisManager.getInstance().getNameSpace(this.NameSpace).get(this.DeviceId);
    }


}
