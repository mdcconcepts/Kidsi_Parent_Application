package org.mdcconcepts.kidsi;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.customitems.ConnectionDetector;
import org.mdcconcepts.kidsi.customitems.InternetConnectionDialog;
import org.mdcconcepts.kidsi.customitems.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {
    //    ImageView imageView1 ;
    EditText EditText_Username, EditText_Password;
    Button ButtonController_Login;
    Typeface font;
    TextView TextViewController_Title;
    ConnectionDetector connectionDetector;
    public ImageView Incomming_Bus;
    private ImageView Kid;
    private ProgressBar progressBar;
    Boolean isConnected;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    TextView TextViewController_ForgotPassword;
    String err = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            Intent intent = getIntent();

            EditText_Username = (EditText) findViewById(R.id.username);
            EditText_Password = (EditText) findViewById(R.id.password);

            EditText_Username.setText(AppSharedPreferences.getUname(LoginActivity.this));
            EditText_Password.setText(AppSharedPreferences.getPassword(LoginActivity.this));
            TextViewController_Title = (TextView) findViewById(R.id.TextViewController_Title);
            TextViewController_ForgotPassword = (TextView) findViewById(R.id.TextViewController_ForgotPassword);
            Incomming_Bus = (ImageView) findViewById(R.id.Incomming_Bus);
            Kid = (ImageView) findViewById(R.id.Kid);
            font = Typeface.createFromAsset(getAssets(), Util.FontName);

            ButtonController_Login = (Button) findViewById(R.id.ButtonController_Login);
            connectionDetector = new ConnectionDetector(LoginActivity.this);


            ButtonController_Login.setTypeface(font);
            EditText_Username.setTypeface(font);
            EditText_Password.setTypeface(font);
            TextViewController_Title.setTypeface(font);
            TextViewController_ForgotPassword.setTypeface(font);
            ButtonController_Login.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    // TODO Auto-generated method stub
                    try {

                        boolean connected = false;
                        isConnected = connectionDetector.isConnectingToInternet();
                        if (isConnected) {
                            if (EditText_Username.getText().toString().trim().isEmpty()) {
                                EditText_Username.setError("Please enter username");

                            } else if (EditText_Password.getText().toString().trim().isEmpty()) {
                                EditText_Password.setError("Please enter password");

                            } else {
//
                                new AttemptLogin().execute();
                            }
                        } else {
                            InternetConnectionDialog internetConnectionDialog = new InternetConnectionDialog(LoginActivity.this);
                            internetConnectionDialog.show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (intent.getStringExtra("error") != null) {
                Util.display(getApplicationContext(), null, intent.getStringExtra("error").toString(), 1, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void animate_bus() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        width = (int) (width * 0.80);
        Animation animation1 = new TranslateAnimation(0.0f, width, 0.0f, 0.0f);
        animation1.setDuration(4000);
        Incomming_Bus.startAnimation(animation1);
        animation1.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(getApplicationContext(), LoadingActivity.class);
                startActivity(i);
                overridePendingTransition(
                        R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                Incomming_Bus.setVisibility(View.INVISIBLE);
            }

            public void onAnimationStart(Animation a) {

                Kid.setVisibility(View.INVISIBLE);
            }

            public void onAnimationRepeat(Animation a) {
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    public class AttemptLogin extends AsyncTask<String, String, String> {
        int success;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting Login ... ");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                // Building Parameters
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair("username", EditText_Username.getText().toString().trim()));
                param.add(new BasicNameValuePair("password", EditText_Password.getText().toString().trim()));

                // Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        Util.LOGIN_URL, "POST", param);

                if (json != null) {// full json response
                    Log.d("Response", json.toString());

                    // json success element
                    success = json.getInt("code");
                    return "";
//                    return json.getString(TAG_MESSAGE);
                } else
                    return "timeout";

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            if (success == 1) {

                Log.d("Success", "login");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do somethin g after 100ms
                        animate_bus();
                    }
                }, 500);
//                    Intent mainIntent = new Intent(LoadingActivity.this, MainActivity.class);
//                    Util.setUSER_NAME(uname);
//
//                    mainIntent.putExtra("mainusername", uname);
//
//                    AppSharedPreferences.setUname(LoadingActivity.this, uname);
//                    AppSharedPreferences.setPassword(LoadingActivity.this, password);
//
//                    LoadingActivity.this.startActivity(mainIntent);
//                    LoadingActivity.this.finish();

            }
        }

    }

//    /**
//     * To check login
//     */
//    private class LoginOperation extends AsyncTask<String, String, String> {
//        InputStream is = null;
//        JSONObject jObj = null;
//        String result = "";
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            findViewById(R.id.imageView1).setVisibility(View.VISIBLE);
//            progressBar = (ProgressBar) findViewById(R.id.progressBar1);
//        }
//
//        @Override
//        protected String doInBackground(String... str) {
//            try {
//
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(loginURL);
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("username", EditText_Username.getText().toString().trim()));
//                params.add(new BasicNameValuePair("password", EditText_Password.getText().toString().trim()));
//                httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                is = httpEntity.getContent();
//
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        is, "iso-8859-1"), 8);
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//                is.close();
//                result = sb.toString();
//                // Log.e("JSON", result);
//            } catch (Exception e) {
//                Log.e("Buffer Error", "Error converting result " + e.toString());
//            }
//
//            // try parse the string to a JSON object
//            try {
//                jObj = new JSONObject(result);
//
//            } catch (JSONException e) {
//                Util.display(null, "Loading Activity JSONException", e.toString(), 0, true);
//            }
//            return result;
//        }
//
//        protected void onPostExecute(String file_url) {
//            try {
//                int code = (jObj.getInt("code"));
//                int userexist = (jObj.getInt("userexist"));
//                String errMsg = null;
//                if (code == 1) {
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Do somethin g after 100ms
//                            animate_bus();
//                        }
//                    }, 500);
////                    Intent mainIntent = new Intent(LoadingActivity.this, MainActivity.class);
////                    Util.setUSER_NAME(uname);
////
////                    mainIntent.putExtra("mainusername", uname);
////
////                    AppSharedPreferences.setUname(LoadingActivity.this, uname);
////                    AppSharedPreferences.setPassword(LoadingActivity.this, password);
////
////                    LoadingActivity.this.startActivity(mainIntent);
////                    LoadingActivity.this.finish();
//
//                } else if (userexist == 0) {
////                    errMsg = "Error:Wrong Username";
////                    Intent mainIntent = new Intent(LoadingActivity.this, LoginActivity.class);
////                    mainIntent.putExtra("error", errMsg);
////                    LoadingActivity.this.startActivity(mainIntent);
////                    LoadingActivity.this.finish();
//
//
//                } else {
////                    errMsg = "Error:Wrong Password";
////                    Intent mainIntent = new Intent(LoadingActivity.this, LoginActivity.class);
////                    mainIntent.putExtra("error", errMsg);
////                    LoadingActivity.this.startActivity(mainIntent);
////                    LoadingActivity.this.finish();
//                }
//
//            } catch (Exception ex) {
//                Util.display(null, "Loading Activity", ex.toString(), 0, true);
//            }
//        }
//    }
}
