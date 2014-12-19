package org.mdcconcepts.kidsi.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ssk on 10/20/2014.
 * FOR GLOBAL VARIABLES
 * tag=0 for Log tag=1 for Toast
 * show true to enable
 *
 * @author SWAPNIL
 */
public class Util {
    public static String gcmxmppid = null;
    // Google Project Number.
    public static final String FontName = "tt0142m_.ttf";

    public static final String GOOGLE_PROJECT_ID = "424039497097";
    public static String USER_NAME;
    public static final String APP_PREFERENCES = "Kidsi_Parent_App";

    public static Boolean LoginTaskStatus=false;

    public static int FOOD_RATINGS=0;
    public static int SLEEP_RATINGS=0;
    public static int MILK_RATINGS=0;

    public static void display(Context context, String tag, String message, int type, boolean show) {
        if (type == 0 && show) {
            Log.d(tag, message);
        }
        if (type == 1 && show) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void setGcmxmppid(String id) {
        gcmxmppid = id;
    }

    public static String getGcmxmppid() {
        return gcmxmppid;
    }

    public static void setUSER_NAME(String un) {
        USER_NAME = un;
    }

    public static String getUSER_NAME() {
        return USER_NAME;
    }

    public static String USER_TYPE="3";

    public static String LOGIN_URL = " http://192.168.100.138:8080/kidsi/index.php?r=userMaster/Login";

    public static String HEALTH_REPORT_URL = "http://192.168.100.138:8080/kidsi/index.php?r=userMaster/studenthealthreport";

    public static String PERFORMANCE_REPORT_URL = "http://192.168.100.138:8080/kidsi/index.php?r=userMaster/Studentperformancereport";

    public static String SCHOOL_INFO_URL="http://192.168.100.138:8080/kidsi/index.php?r=schoolMaster/schoolinfo";

    public static String TEACHER_INFO_PROFILE="http://192.168.100.138:8080/kidsi/index.php?r=teacherMaster/teacherinfo";

    public static String PARENT_INFO_PROFILE="http://192.168.100.138:8080/kidsi/index.php?r=userMaster/parentInformation";

    public static String RATE_TEACHER="http://192.168.100.138:8080/kidsi/index.php?r=userMaster/Addteacherrating";

    public static String FORGOT_PASSWORD="http://192.168.100.138:8080/kidsi/index.php?r=userMaster/Forgetpass";

    public static String UPDATE_PARENT_INFO="http://192.168.100.138:8080/kidsi/index.php?r=userMaster/Updateparentinfo";
}
