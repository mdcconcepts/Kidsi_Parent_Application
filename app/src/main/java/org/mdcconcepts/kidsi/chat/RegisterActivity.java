package org.mdcconcepts.kidsi.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    AtomicInteger ccsMsgId = new AtomicInteger();
    String regId;

    GoogleCloudMessaging gcm;
    Context context;
    MessageSender messageSender;
    String signUpUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        messageSender = new MessageSender();
        Bundle dataBundle;
        context = getApplicationContext();
        gcm = GoogleCloudMessaging.getInstance(this);
        try {
            if (Util.getGcmxmppid() == null) {

                /**
                 * Get GCM Register ID
                 */
                String regId = registerGCM();
                Util.setGcmxmppid(regId);
                signUpUser = Util.USER_NAME;

                try {
                    dataBundle = new Bundle();
                    dataBundle.putString("ACTION", "SIGNUP");
                    dataBundle.putString("USER_NAME", Util.getUSER_NAME());
                    messageSender.sendMessage(dataBundle, gcm);
                } catch (Exception ex) {
                    Log.d("Exception in 1st part ------- " + Util.getUSER_NAME(), ex.toString());
                }
            }

            try {
                //gcm = GoogleCloudMessaging.getInstance(this);

                dataBundle = new Bundle();
                dataBundle.putString("ACTION", "USERLIST");
                dataBundle.putString("USER_NAME", signUpUser);
                messageSender.sendMessage(dataBundle, gcm);
            } catch (Exception ex) {
                Log.d("Exception in 2nd part ------- ", ex.toString());
            }
            Intent i = new Intent(context,
                    UserListActivity.class);
            Log.d(TAG,
                    "onClick of login: Before starting userlist activity.");
            //Util.setUSER_NAME(signUpUser);
            i.putExtra("mainuser", signUpUser);
            startActivity(i);
            finish();
            Log.d(TAG, "onClick of Login: After finish.");


        } catch (Exception ex) {
            Log.d("Exception in Regist ------- ", ex.toString());
        }


    }

    /**
     * This function return GCM ID , if present or create new one
     *
     * @return String GCM REGISTER ID
     */
    public String registerGCM() {


        regId = getRegistrationId();

        if (TextUtils.isEmpty(regId)) {

            registerInBackground();

            Log.d(TAG,
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            Log.d(TAG,
                    "Regid already available: "
                            + regId
            );
        }
        return regId;
    }

    /**
     * This function returns stored GCM ID
     *
     * @return String Stored GCM ID
     */
    private String getRegistrationId() {
        final SharedPreferences prefs = getSharedPreferences(
                RegisterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = 1;//getAppVersion();
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private int getAppVersion() {
        try {
            PackageInfo packageInfo;
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }


    /**
     * This function create new GCM ID
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Util.GOOGLE_PROJECT_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;
                    storeRegistrationId(regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d(TAG, "Error: " + msg);
                }
                Log.d(TAG, "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d(TAG, "Registered with GCM Server." + msg);
            }
        }.execute(null, null, null);
    }

    /**
     * This function used to store new GCM ID in Shared Preference
     *
     * @param regId New GCM ID
     */
    private void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                RegisterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = 1;//getAppVersion();
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
