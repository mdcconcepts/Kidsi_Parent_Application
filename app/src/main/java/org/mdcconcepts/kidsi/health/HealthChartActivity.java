package org.mdcconcepts.kidsi.health;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.customitems.CompleteAsyncTask;
import org.mdcconcepts.kidsi.customitems.ConnectionDetector;
import org.mdcconcepts.kidsi.customitems.InternetConnectionDialog;
import org.mdcconcepts.kidsi.customitems.UniversalAsynckTask;

public class HealthChartActivity extends Activity implements CompleteAsyncTask {

    private RatingBar ratingBar_milk;
    private RatingBar ratingBar_food;
    private RatingBar ratingBar_sleep;

    private InternetConnectionDialog internetConnectionDialog;
    private ConnectionDetector connectionDetector;

    private UniversalAsynckTask universalAsynckTask;

    private Boolean isConnected;
    private Boolean success;

    private String message;

    private JSONObject Temp;

    private AppSharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        initializeViews();
        initializeParameters();

        setStarRatings();

        isConnected = connectionDetector.isConnectingToInternet();
        if (isConnected)
        {

            String[] requestParameters = new String[2];

            requestParameters[0] = Util.HEALTH_REPORT_URL;
            requestParameters[1] = getRequestParameters().toString();

            universalAsynckTask = new UniversalAsynckTask(this, "Processing", HealthChartActivity.this,1);
            universalAsynckTask.execute(requestParameters);

        } else {
            internetConnectionDialog.show();
        }


    }

    public JSONObject getRequestParameters() {
        JSONObject params = new JSONObject();
        try {

            if (!mySharedPreferences.getKidId().isEmpty())
                params.put("student_id", mySharedPreferences.getKidId());

            Log.d("params", params.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private void initializeViews() {
        ratingBar_milk = (RatingBar) findViewById(R.id.ratingBar_milk);
        ratingBar_food = (RatingBar) findViewById(R.id.ratingBar_food);
        ratingBar_sleep = (RatingBar) findViewById(R.id.ratingBar_sleep);
    }

    private void initializeParameters() {
        getActionBar().setTitle("Health");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        internetConnectionDialog = new InternetConnectionDialog(this);

        connectionDetector = new ConnectionDetector(this);

        mySharedPreferences = new AppSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.health, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                // NavUtils.navigateUpFromSameTask(this);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompleteTask(JSONObject jsonObject,int flag) {

        if (jsonObject != null) {

            Log.d("onCompleteTask", jsonObject.toString());
            try {

                success = jsonObject.getBoolean("success");
                message = jsonObject.getString("message");

                if (success) {

                    Temp = jsonObject.getJSONObject("Health_Report");

                    mySharedPreferences.setSleepRating(Temp.getInt("Sleep_Ratings"));
                    mySharedPreferences.setMilkRating(Temp.getInt("Milk_Ratings"));
                    mySharedPreferences.setFoodRating(Temp.getInt("Food_Ratings"));

                    setStarRatings();


                } else {
                    Toast.makeText(HealthChartActivity.this, message, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setStarRatings() {
        ratingBar_milk.setProgress(mySharedPreferences.getMilkRating());
        ratingBar_food.setProgress(mySharedPreferences.getFoodRating());
        ratingBar_sleep.setProgress(mySharedPreferences.getSleepRating());
    }

}
