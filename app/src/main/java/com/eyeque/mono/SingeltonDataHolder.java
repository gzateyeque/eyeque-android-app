package com.eyeque.mono;

import java.util.Calendar;

/**
 * Created by georgez on 6/16/16.
 */
public class SingeltonDataHolder {
    private static SingeltonDataHolder ourInstance = new SingeltonDataHolder();
    public static SingeltonDataHolder getInstance() {
        return ourInstance;
    }

    // Singleton value set
    public static String token = "";
    public static String email = "";
    public static String firstName = "";
    public static String lastName = "";
    public static Integer gender = 1;
    public static Integer birthYear = Calendar.getInstance().get(Calendar.YEAR) - 20;
    public static String deviceSerialNum = "";

    // Singleton method
    public static String getToken() { return token; }
    public static void setToken(String val) { token = val; }
    public static String getEmail() { return email; }
    public static void setEmail(String val) { email = val; }
    public static String getFirstName() { return firstName; }
    public static void setFirstName(String val) { firstName = val; }
    public static String getLastName() { return lastName; }
    public static void setLastName(String val) { lastName = val; }
    public static Integer getGender() { return gender; }
    public static void setGender(Integer val) { gender = val; }
    public static Integer getBirthYear() { return birthYear; }
    public static void setBirthYear(Integer val) { birthYear = val; }
    public static String getDeviceSerialNum() { return deviceSerialNum; }
    public static void setDeviceSerialNum(String val) { deviceSerialNum = val; }

    private SingeltonDataHolder() {}
}
