package com.platform.core.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
    public static final String NameSpace = "users";
    private int Id = -1;
    private String Name = new String("");
    private int Age = -1;
    private String Gender = new String("");

    private String PhoneNumber = new String("");

    public User() {}

    public User(int id) {
        this.Id = id;
    }

    public String getNameSpace() {
        return this.NameSpace;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(this);
        System.out.println(jsonString);

        return jsonString;
    }
    @Override
    public String toString() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("id=" + Id + ",");
        userInfo.append("name=" + Name + ",");
        userInfo.append("age=" + Age + ",");
        userInfo.append("gender=" + Gender + ",");
        userInfo.append("ph=" + PhoneNumber);
        return userInfo.toString();
    }
}
