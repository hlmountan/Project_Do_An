package com.paditech.mvpbase.common.model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hung on 5/12/2018.
 */

public class UserProfile {
    private String name;
    private String email;
    private String photo;
    private String gender;
    private int age;
    private String phoneNumber;
    Boolean isDev = false;
    private ArrayList<ArrayList<String>> followApp;
    private String uid;
    private Boolean requestDev = false;

    public Boolean getRequestDev() {
        return requestDev;
    }

    public void setRequestDev(Boolean requestDev) {
        this.requestDev = requestDev;
    }

    private ArrayList<Map<String,String>> notify;

    public ArrayList<Map<String,String>> getNotify() {
        return notify;
    }


    public void setNotify(ArrayList<Map<String,String>> notify) {
        this.notify = notify;
    }




    public UserProfile(String name, String email, String photo, String gender, int age, String phoneNumber,String uid) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
    }

    public UserProfile() {
    }
    public UserProfile(String name, String email, String photo,String uid) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.uid = uid;
    }

    public ArrayList<ArrayList<String>> getFollowApp() {
        return followApp;
    }

    public void setFollowApp(ArrayList<ArrayList<String>> followApp) {
        this.followApp = followApp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDev() {
        return isDev;
    }

    public void setDev(Boolean dev) {
        isDev = dev;
    }
}
