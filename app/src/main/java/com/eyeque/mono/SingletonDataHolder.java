package com.eyeque.mono;

import java.util.Calendar;

/**
 * Created by georgez on 6/16/16.
 */
public class SingletonDataHolder {
    private static SingletonDataHolder ourInstance = new SingletonDataHolder();
    public static SingletonDataHolder getInstance() {
        return ourInstance;
    }
    private SingletonDataHolder() {}

    // Session value set
    public static int loginType = 1;
    public static Boolean newRegUser = false;
    public static String token = "";
    public static int userId = 0;
    public static String email = "";
    public static String firstName = "";
    public static String lastName = "";
    public static Integer gender = 1;
    public static boolean profileWearEyeglass = false;
    public static Integer birthYear = Calendar.getInstance().get(Calendar.YEAR) - 20;
    public static Integer groupId = 1;
    public static String deviceSerialNum = "";
    public static int testMode = 0;
    public static boolean accommodationOn = true;
    public static int screenProtect = 0;
    public static int wearGlasses = 0;
    public static String urlOdTracking = "";
    public static String urlOSTracking = "";
    public static String urlVisionSummary = "";
    public static int currentTestScore = 0;
    public static int freetrial = 0;
    public static int pupillaryDistance = 0;
    public static double nvadd = 0;
    public static Boolean eyeglassNumPurchasable= false;

    // Eyeglass Number
    public static class EyeglassNumber {
        public double odSph;
        public double odCyl;
        public int odAxis;
        public double osSph;
        public double osCyl;
        public int osAxis;
        public String createdAt;

        public EyeglassNumber(double odSphVal, double odCylVal, int odAxisVal,
                              double osSphVal, double osCylVal, int osAxisVal, String timeVal) {
            odSph = odSphVal;
            odCyl = odCylVal;
            odAxis = odAxisVal;
            osSph = osSphVal;
            osCyl = osCylVal;
            osAxis = osAxisVal;
            createdAt = timeVal;
        }
    }
    public static EyeglassNumber[] eyeglassNumberList;
    public static int eyeglassNumCount = 0;

    // Session method
    // public static int getLoginType() { return loginType; }
    // public static void setLoginType(int val) { loginType = val; }
    // public static String getToken() { return token; }
    // public static void setToken(String val) { token = val; }
    // public static String getEmail() { return email; }
    // public static void setEmail(String val) { email = val; }
    // public static String getFirstName() { return firstName; }
    // public static void setFirstName(String val) { firstName = val; }
    // public static String getLastName() { return lastName; }
    // public static void setLastName(String val) { lastName = val; }
    // public static Integer getGender() { return gender; }
    // public static void setGender(Integer val) { gender = val; }
    // public static Integer getBirthYear() { return birthYear; }
    // public static void setBirthYear(Integer val) { birthYear = val; }
    // public static String getDeviceSerialNum() { return deviceSerialNum; }
    // public static void setDeviceSerialNum(String val) { deviceSerialNum = val; }

    // Phone parameters
    public static String phoneManufacturer = "";
    public static String phoneBrand = "";
    public static String phoneProduct = "";
    public static String phoneModel = "";
    public static String phoneType = "";
    public static int phonePpi = 577;
    public static String deviceName = "EQ101";
    public static double deviceWidth = 2.0f;
    public static double deviceHeight = 1.375f;
    // Pattern drawing configuration
    public static int centerX = 0;
    public static int centerY = 0;
    public static int lineLength = 0;
    public static int lineWidth = 0;
    public static int initDistance = 0;
    public static int minDistance = 0;
    public static int maxDistance = 0;
    /*** For video shooting
     public static int initDistance = 99;
     public static int minDistance = 0;
     public static int maxDistance = 183;
     ***/
    public static final int[] patternAngleList  = {0, 320, 280, 240, 200, 160, 120, 80, 40};
    public static final double[] calcAngleList = {0.0, 320.0, 280.0, 240.0, 200.0, 160.0, 120.0, 80.0, 40.0};
    public static final int[] rotateAngleList = {180, 40, 80, 120, 160, 200, 240, 280, 320};
    // Color configuration
    public static int redDegree = 0;
    public static int greenDegree = 0;
    public static int blueDegree = 0;
    // Calculation configuration
    public static double sphericalStep0 = 1.428405590E-01*577/phonePpi;
    public static double sphericalStep1 = 1.276384330E-01*577/phonePpi;
    public static double sphericalStep2 = 6.454937531E-05*(577/phonePpi)*(577/phonePpi);

    /*****
     // Phone profile for Galaxy 6
     public static String phoneBrand = "Samsung";      // Need to add it to database
     public static String phoneModel = "SM-G930F";     // Need to add it to database
     public static String phoneType = "Galaxy 6";
     public static int phonePpi = 577;
     // Device configuration
     public static String deviceName = "EQ100";
     public static double DEVICE_WIDTH = 2.0f;
     public static double DEVICE_HEIGHT = 1.375f;
     // Pattern drawing configuration
     public static int CENTER_X = 720;
     public static int CENTER_Y = 520;
     public static int LINE_LENGTH = 130;
     public static int LINE_WIDTH = 36;
     public static int INIT_DISTANCE = 326;
     public static int MIN_DISTANCE = 227;
     public static int MAX_DISTANCE = 410;
     /*** For Video Shooting App
     public static int INIT_DISTANCE = 99;
     public static int MIN_DISTANCE = 0;
     public static int MAX_DISTANCE = 183;
     public static final int[] PATTERN_ANGLE_LIST  = {0, 320, 280, 240, 200, 160, 120, 80, 40};
     public static final double[] CALC_ANGLE_LIST = {0.0, 320.0, 280.0, 240.0, 200.0, 160.0, 120.0, 80.0, 40.0};
     public static final int[] ROTATE_ANGLE_LIST = {180, 40, 80, 120, 160, 200, 240, 280, 320};
     // Color configuration
     public static int RED_DEGREE = 0;
     public static int GREEN_DEGREE = 0;
     public static int BLUE_DEGREE = 0;
     // Calculation configuration
     public static double SphericalStep0 = 1.428405590E-01*577/PHONE_PPI;
     public static double SphericalStep1 = 1.276384330E-01*577/PHONE_PPI;
     public static double SphericalStep2 = 6.454937531E-05*(577/PHONE_PPI)*(577/PHONE_PPI);

     // Phone profile for Galaxy 5
     public static String PHONE_BRAND = "Samsung";      // Need to add it to database
     public static String PHONE_MODEL = "SAMSUNG-SM-G900A";     // Need to add it to database
     public static String PHONE_TYPE = "Galaxy 5";
     public static int PHONE_PPI = 432;
     // Device configuration
     public static String DEVICE_NAME = "EQ100";
     public static double DEVICE_WIDTH = 2.0f;
     public static double DEVICE_HEIGHT = 1.375f;
     // Pattern drawing configuration
     public static int CENTER_X = 540;
     public static int CENTER_Y = 390;
     public static int LINE_LENGTH = 98;
     public static int LINE_WIDTH = 27;
     public static int INIT_DISTANCE = 326*PHONE_PPI/577;
     public static int MIN_DISTANCE = 227*PHONE_PPI/577;
     public static int MAX_DISTANCE = 410*PHONE_PPI/577;

     // Phone profile for Nexus 6P
     public static String PHONE_BRAND = "Huawei";      // Need to add it to database
     public static String PHONE_MODEL = "Nexus 6P";     // Need to add it to database
     public static String PHONE_TYPE = "Nexus 6P";
     public static int PHONE_PPI = 518;
     // Device configuration
     public static String DEVICE_NAME = "Device 5";
     public static double DEVICE_WIDTH = 2.0f;
     public static double DEVICE_HEIGHT = 1.375f;
     // Pattern drawing configuration
     public static int CENTER_X = 720;
     public static int CENTER_Y = 500;
     public static int LINE_LENGTH = 130*PHONE_PPI/562;
     public static int LINE_WIDTH = 36*PHONE_PPI/562;
     public static int INIT_DISTANCE = 326*PHONE_PPI/562;
     public static int MIN_DISTANCE = 227*PHONE_PPI/562;
     public static int MAX_DISTANCE = 410*PHONE_PPI/562;

     // Phone profile for LG G5
     public static String PHONE_BRAND = "LGE";
     public static String PHONE_MODEL = "LG-H860";
     public static String PHONE_TYPE = "LG G5";
     public static int PHONE_PPI = 554;
     // Device configuration
     public static String DEVICE_NAME = "Device 5";
     public static double DEVICE_WIDTH = 2.0f;
     public static double DEVICE_HEIGHT = 1.375f;
     // Pattern drawing configuration
     public static int CENTER_X = 720;
     public static int CENTER_Y = 520;
     public static int LINE_LENGTH = 130*PHONE_PPI/562;
     public static int LINE_WIDTH = 36*PHONE_PPI/562;
     public static int INIT_DISTANCE = 326*PHONE_PPI/562;
     public static int MIN_DISTANCE = 227*PHONE_PPI/562;
     public static int MAX_DISTANCE = 410*PHONE_PPI/562;


     public static String PHONE_TYPE = "HTC One S9";
     public static String PHONE_BRAND = "HTC";      // Need to add it to database
     public static String PHONE_MODEL = "HTC_S9u";     // Need to add it to database
     public static int PHONE_PPI = 441;
     // Device configuration
     public static String DEVICE_NAME = "Device 5";
     public static double DEVICE_WIDTH = 2.0f;
     public static double DEVICE_HEIGHT = 1.375f;
     // Pattern drawing configuration
     public static int CENTER_X = 540;
     public static int CENTER_Y = 385;
     public static int LINE_LENGTH = 130*PHONE_PPI/577;
     public static int LINE_WIDTH = 36*PHONE_PPI/577;
     public static int INIT_DISTANCE = 326*PHONE_PPI/577+1;
     public static int MIN_DISTANCE = 227*PHONE_PPI/577;
     public static int MAX_DISTANCE = 410*PHONE_PPI/577;
     ****/


}
