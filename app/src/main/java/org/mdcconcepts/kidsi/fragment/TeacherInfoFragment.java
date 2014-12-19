package org.mdcconcepts.kidsi.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.chat.ChatRoomActivity;
import org.mdcconcepts.kidsi.customitems.CompleteAsyncTask;
import org.mdcconcepts.kidsi.customitems.CustomTextView;
import org.mdcconcepts.kidsi.customitems.RateTeacherDialog;
import org.mdcconcepts.kidsi.customitems.UniversalAsynckTask;


public class TeacherInfoFragment extends Fragment implements View.OnClickListener, CompleteAsyncTask {

    public TeacherInfoFragment() {
    }

    static View rootView = null;

    private CustomTextView tvEmailAddrs;
    private CustomTextView tvClass;
    private CustomTextView tvEmergancyNumber;
    private CustomTextView tvMobileNumber;
    private CustomTextView tvTeacherName;
    private CustomTextView tvTeacherStatus;
    private CustomTextView tvTeacherTotalCount;



    private ImageView imgCall;
    private ImageView imgEmergancyCall;

    private RatingBar ratingBar_teacher_rating;

    private UniversalAsynckTask universalAsynckTask;

    private Boolean success;
    private String message;
    private JSONObject temp;
    private AppSharedPreferences mySharedPreferences;

    private Button btnRate;
    private RateTeacherDialog rateTeacherDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.teacher_profile, container, false);
            initailizeViews();
            initParameters();
            setParameters();

            if (mySharedPreferences.getTeacherInfoStatus()) {

            } else {
                String[] requestParameters = new String[2];
                requestParameters[0] = Util.TEACHER_INFO_PROFILE;
                requestParameters[1] = getRequestParameters().toString();
                universalAsynckTask = new UniversalAsynckTask(TeacherInfoFragment.this, "Loading", getActivity(),1);
                universalAsynckTask.execute(requestParameters);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void initParameters() {
        rateTeacherDialog = new RateTeacherDialog(getActivity());
        mySharedPreferences = new AppSharedPreferences(getActivity());
        btnRate.setOnClickListener(this);
    }

    public void setParameters() {


        tvEmailAddrs.setText(mySharedPreferences.getTeacherEmailAddress());
        Linkify.addLinks(tvEmailAddrs, Linkify.EMAIL_ADDRESSES);
        tvEmailAddrs.setLinkTextColor(Color.WHITE);


        tvEmergancyNumber.setText(mySharedPreferences.getTeacherEmergancyNumber());
//        Linkify.addLinks(tvEmergancyNumber, Linkify.PHONE_NUMBERS);
//        tvEmergancyNumber.setLinkTextColor(Color.WHITE);

        tvMobileNumber.setText(mySharedPreferences.getTeacherMobileNumber());
//        Linkify.addLinks(tvMobileNumber, Linkify.PHONE_NUMBERS);
//        tvMobileNumber.setLinkTextColor(Color.WHITE);

        tvClass.setText(mySharedPreferences.getTeacherClassName());
        tvTeacherStatus.setText(mySharedPreferences.getTeacherStatus());
        tvTeacherName.setText(mySharedPreferences.getTeacherName());

        tvTeacherTotalCount.setText("("+ mySharedPreferences.getTotalRateCount() +")");

        ratingBar_teacher_rating.setProgress(mySharedPreferences.getTeacherRating());

    }

    public void callNumber(String mobileNumber) {


        Toast.makeText(getActivity().getApplicationContext(), "Calling", Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String phoneNumber = mobileNumber;
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    public JSONObject getRequestParameters() {
        JSONObject params = new JSONObject();
        try {


            params.put("School_id", mySharedPreferences.getSchoolId());
            params.put("Student_Id", mySharedPreferences.getKidId());
            Log.d("params", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private void initailizeViews() {

        tvEmailAddrs = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_email_addrs);
        tvClass = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_class);
        tvEmergancyNumber = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_emergancy_number);
        tvMobileNumber = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_mobile_number);
        tvTeacherName = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_name);
        tvTeacherStatus = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_status);
        tvTeacherTotalCount = (CustomTextView) rootView.findViewById(R.id.teacher_profile_textview_total_count);

        imgCall = (ImageView) rootView.findViewById(R.id.teacher_profile_img_call);
        imgEmergancyCall = (ImageView) rootView.findViewById(R.id.teacher_profile_img_emergancy_call);

        ratingBar_teacher_rating = (RatingBar) rootView.findViewById(R.id.ratingBar_teacher_rating);
        btnRate = (Button) rootView.findViewById(R.id.teacher_profile_btn_rate);
    }

    @Override
    public void onCompleteTask(JSONObject jsonObject,int flag) {
        if (jsonObject != null) {
            Log.d("Teacher Info Fragment", jsonObject.toString());
            try {
                success = jsonObject.getBoolean("success");
                message = jsonObject.getString("message");

                if (success) {
                    temp = jsonObject.getJSONObject("teacher_info");
                    Log.d("Temp", temp.toString());
                    setMySharedPreferences();
                    setParameters();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            try {
                message = jsonObject.getString("message");
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setMySharedPreferences() {
        try {
            mySharedPreferences.setTeacherEmailAddress(temp.getString("Email_Id"));
            mySharedPreferences.setTeacherEmergancyNumber(temp.getString("Emergancy_Number"));
            mySharedPreferences.setTeacherMobileNumber(temp.getString("Mobile_Number"));
            mySharedPreferences.setTeacherName(temp.getString("Teacher_Name"));
            mySharedPreferences.setTeacherStatus(temp.getString("Status"));
            mySharedPreferences.setTeacherClassName(temp.getString("class"));
            mySharedPreferences.setTeacherRating(temp.getInt("Star_Rating"));
            mySharedPreferences.setTeacherId(temp.getString("teacher_id"));
            mySharedPreferences.setTeacherInfoStatus(true);
            mySharedPreferences.setTotalRateCount(temp.getString("rating_count"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.teacher_profile_img_call:
                callNumber(tvMobileNumber.getText().toString().trim());
                break;
            case R.id.teacher_profile_img_emergancy_call:
                callNumber(tvEmergancyNumber.getText().toString().trim());
                break;
            case R.id.teacher_profile_btn_rate:
                rateTeacherDialog.show();
                break;

        }
    }
}
