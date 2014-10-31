package org.mdcconcepts.kidsi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;

public class HomeActivity extends Activity {
//    ImageView imageView1 ;
    EditText EditText_Username,EditText_Password;
    Button ButtonController_Login;
    Typeface font;

    String err=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
        try {
            Intent intent = getIntent();

            EditText_Username = (EditText) findViewById(R.id.username);
            EditText_Password = (EditText) findViewById(R.id.password);

            EditText_Username.setText(AppSharedPreferences.getUname(HomeActivity.this));
            EditText_Password.setText(AppSharedPreferences.getPassword(HomeActivity.this));
//            imageView1 = (ImageView) findViewById(R.id.login);

            font=Typeface.createFromAsset(getAssets(),Util.FontName);

            ButtonController_Login =(Button)findViewById(R.id.ButtonController_Login);

            ButtonController_Login.setTypeface(font);
            EditText_Username.setTypeface(font);
            EditText_Password.setTypeface(font);

            ButtonController_Login.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

//                    imageView1.setAlpha(60);
                    // TODO Auto-generated method stub
                    try {

                        boolean connected = false;
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                /* if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;

                }
               if (connected == false) {
                    Util.display(getApplicationContext(),null,"Please connect to internet",1,true);
                    imageView1.setAlpha(255);
                }

                else
                {*/

                        if (EditText_Username.getText().toString().trim().isEmpty()) {
                            EditText_Username.setError("Please enter username");
//                            err = "Please enter username";
//                            imageView1.setAlpha(255);
//                            Util.display(getApplicationContext(), null, err, 1, true);

                        } else if (EditText_Password.getText().toString().trim().isEmpty()) {
                            EditText_Password.setError("Please enter password");
//                            err = "Please enter password";
//                            imageView1.setAlpha(255);
//                            Util.display(getApplicationContext(), null, err, 1, true);

                        } else {
                            Intent i = new Intent(getApplicationContext(), LoadingActivity.class);
                            i.putExtra("mainusername", EditText_Username.getText().toString().trim());
                            i.putExtra("password", EditText_Password.getText().toString().trim());
                            startActivity(i);
//                            HomeActivity.this.finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  }
                }
            });
            if (intent.getStringExtra("error") != null) {
                Util.display(getApplicationContext(), null, intent.getStringExtra("error").toString(), 1, true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

}
