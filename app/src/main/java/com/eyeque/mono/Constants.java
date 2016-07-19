package com.eyeque.mono;

/**
 * Created by georgez on 3/2/16.
 */
import android.provider.BaseColumns;

public class Constants {

    public final static String BuildNumber = "0.9.1";
    public static final double PI = 3.141592653589793d;

    // Restful API Call Base URL Address
    public final static String LocalRestfulBaseURL = "http://192.168.110.122:8080";
    public final static String AwsEc2RestfulBaseURL = "http://54.191.245.62:8080";
    // public final static String LocalRestfulBaseURL = "http://192.168.110.85:8080";
    public final static String AccessToken = "e46cghc52pqd8kvgqmv8ovsi1ufcfetg";
    public static String RestfulBaseURL = AwsEc2RestfulBaseURL;
    public static String apiRestfulBaseURL = "http://api.eyeque.com/";
    public static String ContentRestFulBaseURL = "http://api.eyeque.com:8080/";
    // public final static String AccessToken = "472h0onmgk3o18bn8kj629m8s2tke6k0";

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
    public static final String UrlYoutube = "http://54.201.220.133:8080/tutorial_video/index.html";
    public static final String UrlTermsOfService = ContentRestFulBaseURL + "user_agreement/terms";
    public static final String UrlPrivacyPolicy = ContentRestFulBaseURL + "user_agreement/policy";
    public static final String UrlBuyDevice = apiRestfulBaseURL + "eyeque-pocket-refractor.html";
    public static final String UrlTrackingDataOd = ContentRestFulBaseURL + "axis_component/sphod.html";
    public static final String UrlTrackingDataOs = ContentRestFulBaseURL + "axis_component/sphos.html";

    // Restful API URL
    // public static final String UrlSignIn = "http://54.201.218.215/index.php/rest/V1/integration/customer/token";
    public static final String UrlSignIn = apiRestfulBaseURL + "index.php/rest/V1/integration/customer/token";
    public static final String UrlSignUp = apiRestfulBaseURL + "index.php/rest/V1/customers";
    public static final String UrlUserProfile = apiRestfulBaseURL + "index.php/rest/V1/customers/me";
    public static final String UrlForgotPassword = apiRestfulBaseURL + "index.php/rest/V1/customers/password";
    public static final String UrlCheckSerialNum = apiRestfulBaseURL + "index.php/rest/V1/sncheck/check";
    public static final String UrlVerifySocMediaLogin = apiRestfulBaseURL + "index.php/rest/V1/socialcustomers/validate";
    public static final String UrlSocMediaSignUp = apiRestfulBaseURL + "index.php/rest/V1/socialcustomers";

    // Database parameters
    public static final String DB_NAME = "mono.db";
    public static final int DB_VERSION = 1;
    public static final String USER_ENTITY_TABLE = "user_entity";
    public static final String TEST_SUBJECT_TABLE = "test_subject";
    public static final String TEST_RESULT_TABLE = "test_result";
    public static final String TEST_MEASUREMENT_TABLE = "test_measurement";

    // Columns for user entity table
    public static final String USER_ENTITY_ID_COLUMN  = BaseColumns._ID;
    public static final String USER_ENTITY_VERSION_COLUMN  = "version";
    public static final String USER_ENTITY_EMAIL_COLUMN  = "email";
    public static final String USER_ENTITY_TOKEN_COLUMN  = "token";
    public static final String USER_ENTITY_NAME_COLUMN  = "name";
    public static final String USER_ENTITY_GENDER_COLUMN  = "gender";
    public static final String USER_ENTITY_DEVICE_SERIAL_COLUMN  = "device_serial";
    public static final String USER_ENTITY_SIGNUP_STATUS = "signup_status";
    public static final String USER_ENTITY_CREATED_AT_COLUMN  = "created_at";
    public static final String USER_ENTITY_LAST_SYNCED_AT_COLUMN  = "last_synced_at";

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

    /*****
    public void setRestfulBaseURL(int number) {
        if (number > 0)
            RestfulBaseURL = AwsEc2RestfulBaseURL;
        else
            RestfulBaseURL = LocalRestfulBaseURL;
    }
    *****/

}
