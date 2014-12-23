package org.mdcconcepts.kidsi;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.customitems.CompleteAsyncTask;
import org.mdcconcepts.kidsi.customitems.ConnectionDetector;
import org.mdcconcepts.kidsi.customitems.ForgotPasswordDialog;
import org.mdcconcepts.kidsi.customitems.InternetConnectionDialog;
import org.mdcconcepts.kidsi.customitems.JSONParser;
import org.mdcconcepts.kidsi.customitems.UniversalAsynckTask;

public class LoginActivity extends Activity implements OnClickListener, CompleteAsyncTask {
    //    ImageView imageView1 ;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private JSONParser jsonParser = new JSONParser();

    private EditText EditText_Username, EditText_Password;

    private Button ButtonController_Login;

    private Typeface font;

    private TextView TextViewController_Title;
    private TextView TextViewController_ForgotPassword;

    private ConnectionDetector connectionDetector;

    private ImageView Incomming_Bus;
    private ImageView Kid;

    private ProgressBar progressBar;

    private Boolean isConnected;
    private Boolean success;

    private String err = null;
    private String message = "";

    private InternetConnectionDialog internetConnectionDialog;

    private FrameLayout frameLayout_main;

    private UniversalAsynckTask asyncTask;

    private JSONObject Temp;
    private AppSharedPreferences mySharedPreferences;
    private ForgotPasswordDialog passwordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            initView();
            initParameters();
            setFont();
//            setBackground();

            Intent intent = getIntent();
            if (intent.getStringExtra("error") != null) {
                Util.display(getApplicationContext(), null, intent.getStringExtra("error").toString(), 1, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initView() {

        EditText_Username = (EditText) findViewById(R.id.username);

        EditText_Password = (EditText) findViewById(R.id.password);

        TextViewController_Title = (TextView) findViewById(R.id.TextViewController_Title);

        TextViewController_ForgotPassword = (TextView) findViewById(R.id.TextViewController_ForgotPassword);

        Incomming_Bus = (ImageView) findViewById(R.id.Incomming_Bus);
//
        Kid = (ImageView) findViewById(R.id.Kid);

        ButtonController_Login = (Button) findViewById(R.id.ButtonController_Login);

    }

    private void initParameters() {

        mySharedPreferences = new AppSharedPreferences(this);
        EditText_Username.setText(mySharedPreferences.getUname());
//        EditText_Password.setText(mySharedPreferences.getPassword());
        connectionDetector = new ConnectionDetector(LoginActivity.this);
        internetConnectionDialog = new InternetConnectionDialog(LoginActivity.this);
        passwordDialog=new ForgotPasswordDialog(this);
        ButtonController_Login.setOnClickListener(this);
        TextViewController_ForgotPassword.setOnClickListener(this);
    }

    private void setFont() {

        font = Typeface.createFromAsset(getAssets(), Util.FontName);

        ButtonController_Login.setTypeface(font);
        EditText_Username.setTypeface(font);
        EditText_Password.setTypeface(font);
        TextViewController_Title.setTypeface(font);
        TextViewController_ForgotPassword.setTypeface(font);

    }


    public void animate_bus() {
        ButtonController_Login.setEnabled(false);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ButtonController_Login:
                try {

                    boolean connected = false;
                    isConnected = connectionDetector.isConnectingToInternet();
                    if (isConnected) {
                        if (EditText_Username.getText().toString().trim().isEmpty()) {
                            EditText_Username.setError("Please enter username");

                        } else if (EditText_Password.getText().toString().trim().isEmpty()) {
                            EditText_Password.setError("Please enter password");

                        } else {

                            String[] requestParameters = new String[2];

                            requestParameters[0] = Util.LOGIN_URL;
                            requestParameters[1] = getRequestParameters().toString();

                            asyncTask = new UniversalAsynckTask(this, "Attempting Login", LoginActivity.this,1);

//                            if (!Util.LoginTaskStatus) {
                                asyncTask.execute(requestParameters);
//                            }
//                            new AttemptLogin().execute();
                        }
                    } else {

                        internetConnectionDialog.show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.TextViewController_ForgotPassword:
                passwordDialog.show();
                break;
        }
    }

    public JSONObject getRequestParameters() {
        JSONObject params = new JSONObject();
        try {

            params.put("username", EditText_Username.getText().toString().trim());
            params.put("password", EditText_Password.getText().toString().trim());

            params.put("user_type_id", Util.USER_TYPE);

            Log.d("params", params.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public void onCompleteTask(JSONObject jsonObject,int flag) {

        try {

            Util.LoginTaskStatus = false;

            if (jsonObject != null) {

                Log.d("onCompleteTask Response", jsonObject.toString());

                success = jsonObject.getBoolean("success");
                message = jsonObject.getString("message");


                if (success) {
                    Temp = jsonObject.getJSONObject("Parent_Info");
                    Log.d("Temp", Temp.toString());
                    Log.d("Success", "login");
                    setPreferences();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            animate_bus();
                        }
                    }, 500);

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPreferences() throws Exception {

        mySharedPreferences.setUname(EditText_Username.getText().toString().trim());
        mySharedPreferences.setPassword(EditText_Password.getText().toString().trim());

        mySharedPreferences.setParentId(Temp.getString("parent_id"));
        mySharedPreferences.setSchoolId(Temp.getString("school_id"));
        mySharedPreferences.setKidId(Temp.getString("student_id"));
    }
}
