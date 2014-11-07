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

    public static String LOGIN_URL = "http://swapnil.mdcconcepts.com/gcm_server_files/checklogin.php";
}
