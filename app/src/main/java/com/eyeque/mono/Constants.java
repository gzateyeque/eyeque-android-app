package com.eyeque.mono;

import android.os.Build;

/**
 * Created by georgez on 3/2/16.
 */
import android.provider.BaseColumns;

public class Constants {

    public final static String BuildNumber = "1.0.1 Pre Release C";
    public static final double PI = 3.141592653589793d;
    public final static int sdkVersion = Build.VERSION.SDK_INT;

    // Phone Configuration

    // Restful API Call Base URL Address
    public final static String LocalRestfulBaseURL = "http://192.168.110.122:8080";
    public final static String AwsEc2RestfulBaseURL = "http://54.191.245.62:8080";
    // public final static String LocalRestfulBaseURL = "http://192.168.110.85:8080";
    public final static String AccessToken = "e46cghc52pqd8kvgqmv8ovsi1ufcfetg";
    public static String RestfulBaseURL = AwsEc2RestfulBaseURL;
    public static String ApiRestfulBaseURL = "http://apidev.eyeque.com/";
    public static String ContentRestFulBaseURL = "http://apidev.eyeque.com:8080/";
    public static String UserdataRestfulBaseURL = "http://apidev.eyeque.com:8987/";

    // Line distance scale parameters
    public static final int MINVAL_DEVICE_1 = 270;
    public static final int MINVAL_DEVICE_3 = 227;
    public static final int MINVAL_DEVICE_5 = 227;
    public static final int MINVAL_DEVICE_6 = 130;
    public static final int MAXVAL_DEVICE_1 = 60;
    public static final int MAXVAL_DEVICE_3 = 183;
    public static final int MAXVAL_DEVICE_5 = 183;
    public static final int MAXVAL_DEVICE_6 = 140;

    public final static int NETCONN_TIMEOUT_VALUE = 10000;

    // Content URL
    public static final String UrlBanner = ContentRestFulBaseURL + "banner";
    public static final String UrlYoutube = ContentRestFulBaseURL + "tutorial_video/index.html";
    public static final String UrlTermsOfService = ContentRestFulBaseURL + "user_agreement/terms";
    public static final String UrlPrivacyPolicy = ContentRestFulBaseURL + "user_agreement/policy";
    public static final String UrlFaq =  "http://54.201.220.133:8080/tmpweb/#/page/faq";

    // Restful API Call URL
    public static final String UrlBuyDevice = ApiRestfulBaseURL + "eyeque-mini-scope.html";
    public static final String UrlTrackingDataOd = UserdataRestfulBaseURL + "index.html?hash=default&column=sphEOD";
    public static final String UrlTrackingDataOs = UserdataRestfulBaseURL + "index.html?hash=default&column=sphEOS";
    public static final String UrlSignIn = ApiRestfulBaseURL + "index.php/rest/V1/integration/customer/token";
    public static final String UrlSignUp = ApiRestfulBaseURL + "index.php/rest/V1/customers";
    public static final String UrlUserProfile = ApiRestfulBaseURL + "index.php/rest/V1/customers/me";
    public static final String UrlForgotPassword = ApiRestfulBaseURL + "index.php/rest/V1/customers/password";
    public static final String UrlCheckSerialNum = ApiRestfulBaseURL + "index.php/rest/V1/sncheck/check";
    public static final String UrlVerifySocMediaLogin = ApiRestfulBaseURL + "index.php/rest/V1/socialcustomers/validate";
    public static final String UrlSocMediaSignUp = ApiRestfulBaseURL + "index.php/rest/V1/socialcustomers";
    public static final String UrlPhoneConfig = ApiRestfulBaseURL + "index.php/rest/eyecloud/api/V2/devices";
    public static final String UrlUploadTest = ApiRestfulBaseURL + "index.php/rest/eyecloud/api/V2/tests";
    public static final String UrlConfirmTest = ApiRestfulBaseURL + "index.php/rest/eyecloud/api/V2/discardtest";
    public static final String UrlDashboard = ApiRestfulBaseURL + "index.php/rest/eyecloud/api/V2/getdashboardinfo";
    public static final String UrlPurchaseEyeglassNumber = ApiRestfulBaseURL + "index.php/rest/eyecloud/api/V2/purchaseeyeglassnumbers";

    // Database parameters
    public static final String DB_NAME = "scope.db";
    public static final int DB_VERSION = 1;
    public static final String USER_ENTITY_TABLE = "user_entity";
    public static final String TEST_SUBJECT_TABLE = "test_subject";
    public static final String TEST_RESULT_TABLE = "test_result";
    public static final String TEST_MEASUREMENT_TABLE = "test_measurement";

    // Columns for user entity table
    public static final String USER_ENTITY_ID_COLUMN  = BaseColumns._ID;
    public static final String USER_ENTITY_VERSION_COLUMN  = "version";
    // public static final String USER_ENTITY_EMAIL_COLUMN  = "email";
    public static final String USER_ENTITY_TOKEN_COLUMN  = "token";
    // public static final String USER_ENTITY_NAME_COLUMN  = "name";
    // public static final String USER_ENTITY_GENDER_COLUMN  = "gender";
    // public static final String USER_ENTITY_DEVICE_SERIAL_COLUMN  = "device_serial";
    // public static final String USER_ENTITY_SIGNUP_STATUS = "signup_status";
    // public static final String USER_ENTITY_CREATED_AT_COLUMN  = "created_at";
    // public static final String USER_ENTITY_LAST_SYNCED_AT_COLUMN  = "last_synced_at";

    // Columns for test_subject table
    public static final String TEST_SUBJECT_ID_COLUMN  = BaseColumns._ID;
    public static final String TEST_SUBJECT_VERSION_COLUMN  = "version";
    public static final String TEST_SUBJECT_FIRST_NAME_COLUMN  = "first_name";
    public static final String TEST_SUBJECT_LAST_NAME_COLUMN  = "last_name";
    public static final String TEST_SUBJECT_EMAIL_COLUMN  = "email";
    public static final String TEST_SUBJECT_CREATED_AT_COLUMN  = "created_at";
    public static final String TEST_SUBJECT_LAST_SYNCED_AT_COLUMN  = "last_synced_at";
    public static final String TEST_SUBJECT_STATUS  = "status";

    // Columns for test_result table
    public static final String TEST_RESULT_ID_COLUMN  = BaseColumns._ID;
    public static final String TEST_RESULT_VERSION_COLUMN  = "version";
    public static final String TEST_RESULT_SUBJECT_ID_COLUMN  = "subject_id";
    public static final String TEST_RESULT_DEVICE_ID_COLUMN  = "device_id";
    public static final String TEST_RESULT_DEVICE_NAME_COLUMN  = "device_name";
    public static final String TEST_RESULT_BEAM_SPILTTER_COLUMN  = "beam_splitter";
    public static final String TEST_RESULT_SPH_OD_COLUMN  = "sphod";
    public static final String TEST_RESULT_SPH_OS_COLUMN  = "sphos";
    public static final String TEST_RESULT_CYL_OD_COLUMN  = "cylod";
    public static final String TEST_RESULT_CYL_OS_COLUMN  = "cylos";
    public static final String TEST_RESULT_AXIS_OD_COLUMN  = "axisod";
    public static final String TEST_RESULT_AXIS_OS_COLUMN  = "axisos";
    public static final String TEST_RESULT_SE_OD_COLUMN  = "seod";
    public static final String TEST_RESULT_SE_OS_COLUMN  = "seos";
    public static final String TEST_RESULT_CREATED_AT_COLUMN  = "created_at";
    public static final String TEST_RESULT_LAST_SYNCED_AT_COLUMN  = "last_synced_at";
    public static final String TEST_RESULT_STATUS  = "status";

    // Columns for test_measurement table
    public static final String TEST_MEASUREMENT_ID_COLUMN  = BaseColumns._ID;
    public static final String TEST_MEASUREMENT_VERSION_COLUMN  = "version";
    public static final String TEST_MEASUREMENT_SUBJECT_ID_COLUMN  = "subject_id";
    public static final String TEST_MEASUREMENT_TEST_ID_COLUMN  = "test_id";  // Reference test_result ID
    public static final String TEST_MEASUREMENT_ANGLE_COLUMN  = "angle";
    public static final String TEST_MEASUREMENT_DISTANCE_COLUMN  = "distance";
    public static final String TEST_MEASUREMENT_POWER_COLUMN  = "power";
    public static final String TEST_MEASUREMENT_RIGHT_EYE_COLUMN  = "right_eye";
    public static final String TEST_MEASUREMENT_CREATED_AT_COLUMN  = "created_at";
    public static final String TEST_MEASUREMENT_LAST_SYNCED_AT_COLUMN  = "last_synced_at";
    public static final String TEST_MEASUREMENT_STATUS  = "status";

    // Phone profile for Galaxy 6
    public static String PHONE_BRAND = "Samsung";      // Need to add it to database
    public static String PHONE_MODEL = "SM-G930F";     // Need to add it to database
    public static String PHONE_TYPE = "Galaxy 6";
    public static int PHONE_PPI = 577;
    // Device configuration
    public static String DEVICE_NAME = "Device 5";
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
     ****/
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

    /*****
     // Phone profile for Galaxy 6
     public static String PHONE_BRAND = "Samsung";      // Need to add it to database
     public static String PHONE_MODEL = "SM-G930F";     // Need to add it to database
     public static String PHONE_TYPE = "Galaxy 6";
     public static int PHONE_PPI = 577;
     // Device configuration
     public static String DEVICE_NAME = "Device 5";
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
     public static String PHONE_MODEL = "SM-G930F";     // Need to add it to database
     public static String PHONE_TYPE = "Galaxy 5";
     public static int PHONE_PPI = 432;
     // Device configuration
     public static String DEVICE_NAME = "Device 5";
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


     // Phone profile for Nexus 6P
     public static String PHONE_BRAND = "Huawei";      // Need to add it to database
     public static String PHONE_MODEL = "SM-G930F";     // Need to add it to database
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

     // Phone profile for LG G5
     public static String PHONE_BRAND = "LG";
     public static String PHONE_MODEL = "SM-G930F";
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

     public static String PHONE_TYPE = "HTC One S9";
     public static String PHONE_BRAND = "Samsung";      // Need to add it to database
     public static String PHONE_MODEL = "SM-G930F";     // Need to add it to database
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
     ****/


}
