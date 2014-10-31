package org.mdcconcepts.kidsi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends Activity {
    private ProgressBar progressBar;

    String uname,password;

    private static String loginURL = "http://swapnil.mdcconcepts.com/gcm_server_files/checklogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        try {
            Intent i = getIntent();
            uname = i.getStringExtra("mainusername");
            password = i.getStringExtra("password");
            //  Log.d("IN PROGRSS ",uname);

            new LoginOperation().execute(uname, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loading, menu);
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


    /**
     * To check login
     */
    private class LoginOperation extends AsyncTask<String,String,String>
    {
        InputStream is = null;
        JSONObject jObj = null;
        String result = "";

        protected void onPreExecute() {
            super.onPreExecute();

            findViewById(R.id.imageView1).setVisibility(View.VISIBLE);
            progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        }
        @Override
        protected String doInBackground(String... str) {
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(loginURL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", str[0]));
                params.add(new BasicNameValuePair("password", str[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
               // Log.e("JSON", result);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(result);

            } catch (JSONException e) {
                Util.display(null, "Loading Activity JSONException", e.toString(), 0, true);
            }
            return result;
        }
        protected void onPostExecute(String file_url) {
            try {
                int code = (jObj.getInt("code"));
                int userexist=(jObj.getInt("userexist"));
                String errMsg = null;
                if(code==1) {
                    Intent mainIntent = new Intent(LoadingActivity.this, MainActivity.class);
                    Util.setUSER_NAME(uname);

                    mainIntent.putExtra("mainusername", uname);

                    AppSharedPreferences.setUname(LoadingActivity.this,uname);
                    AppSharedPreferences.setPassword(LoadingActivity.this,password);

                    LoadingActivity.this.startActivity(mainIntent);
                    LoadingActivity.this.finish();

                }
                else if(userexist==0)
                {
                    errMsg="Error:Wrong Username";
                    Intent mainIntent = new Intent(LoadingActivity.this, HomeActivity.class);
                    mainIntent.putExtra("error",errMsg);
                    LoadingActivity.this.startActivity(mainIntent);
                    LoadingActivity.this.finish();


                }
                else
                {
                    errMsg="Error:Wrong Password";
                    Intent mainIntent = new Intent(LoadingActivity.this, HomeActivity.class);
                    mainIntent.putExtra("error",errMsg);
                    LoadingActivity.this.startActivity(mainIntent);
                    LoadingActivity.this.finish();
                }

            }catch(Exception ex)
            {
                Util.display(null,"Loading Activity",ex.toString(),0,true);
            }
        }
    }

}
