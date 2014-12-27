package org.mdcconcepts.kidsi.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.customitems.CompleteAsyncTask;
import org.mdcconcepts.kidsi.customitems.CustomTextView;
import org.mdcconcepts.kidsi.customitems.UniversalAsynckTask;

public class SchooolInfoFragment extends Fragment implements CompleteAsyncTask, View.OnClickListener {

    public SchooolInfoFragment() {
    }

    static View rootView = null;

    CustomTextView tvEmailAddrs;
    CustomTextView tvWebsite;
    CustomTextView tvLandlineNumber;
    CustomTextView tvMobileNumber;
    CustomTextView tvAddress;

    UniversalAsynckTask universalAsynckTask;

    Boolean success;
    String message;
    JSONObject temp;
    AppSharedPreferences mySharedPreferences;

    ImageView imgGPlus;
    ImageView imgFb;
    ImageView imgTwitter;
    ImageView imgLinkedin;
    ImageView imgCallMobile;
    ImageView imgCallLandline;

    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_school_info, container, false);
            initailizeViews();
            setParameters();
            if (mySharedPreferences.getSchoolInfoStatus()) {

            } else {
                String[] requestParameters = new String[2];
                requestParameters[0] = Util.SCHOOL_INFO_URL;
                requestParameters[1] = getRequestParameters().toString();
                universalAsynckTask = new UniversalAsynckTask(SchooolInfoFragment.this, "Loading", getActivity(), 1);
                universalAsynckTask.execute(requestParameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public JSONObject getRequestParameters() {
        JSONObject params = new JSONObject();
        try {
            params.put("schoolid", mySharedPreferences.getSchoolId());
            Log.d("params", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private void initailizeViews() {
        tvEmailAddrs = (CustomTextView) rootView.findViewById(R.id.school_info_textview_email_addrs);
        tvWebsite = (CustomTextView) rootView.findViewById(R.id.school_info_textview_website);
        tvLandlineNumber = (CustomTextView) rootView.findViewById(R.id.school_info_textview_landline_number);
        tvMobileNumber = (CustomTextView) rootView.findViewById(R.id.school_info_textview_mobile_number);
        tvAddress = (CustomTextView) rootView.findViewById(R.id.school_info_textview_address);

        imgGPlus = (ImageView) rootView.findViewById(R.id.school_info_img_g_plus);
        imgFb = (ImageView) rootView.findViewById(R.id.school_info_img_fb);
        imgLinkedin = (ImageView) rootView.findViewById(R.id.school_info_img_linkedin);
        imgTwitter = (ImageView) rootView.findViewById(R.id.school_info_img_twitter);

        imgCallMobile = (ImageView) rootView.findViewById(R.id.school_info_img_call);
        imgCallLandline = (ImageView) rootView.findViewById(R.id.school_info_img_call_landline);
    }

    private void setParameters() {

        mySharedPreferences = new AppSharedPreferences(getActivity());

        tvEmailAddrs.setText(mySharedPreferences.getSchoolEmailId());

        Linkify.addLinks(tvEmailAddrs, Linkify.EMAIL_ADDRESSES);
        tvEmailAddrs.setLinkTextColor(Color.WHITE);

        tvWebsite.setText(mySharedPreferences.getSchoolWebsite());
        Linkify.addLinks(tvWebsite, Linkify.WEB_URLS);
        tvWebsite.setLinkTextColor(Color.WHITE);

        tvLandlineNumber.setText(mySharedPreferences.getSchoolLandlineNumber());
//        Linkify.addLinks(tvLandlineNumber, Linkify.PHONE_NUMBERS);
//        tvLandlineNumber.setLinkTextColor(Color.TRANSPARENT);

        tvMobileNumber.setText(mySharedPreferences.getSchoolMobileNumber());
//        Linkify.addLinks(tvMobileNumber, Linkify.PHONE_NUMBERS);
//        tvMobileNumber.setLinkTextColor(Color.TRANSPARENT);

        tvAddress.setText(mySharedPreferences.getSchoolAddress());
        Linkify.addLinks(tvAddress, Linkify.MAP_ADDRESSES);
        tvAddress.setLinkTextColor(Color.WHITE);

        imgGPlus.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgTwitter.setOnClickListener(this);
        imgLinkedin.setOnClickListener(this);

        imgCallLandline.setOnClickListener(this);
        imgCallMobile.setOnClickListener(this);

    }

    @Override
    public void onCompleteTask(JSONObject jsonObject, int flag) {

        if (jsonObject != null) {
            Log.d("School Info Fragment", jsonObject.toString());
            try {
                success = jsonObject.getBoolean("success");
                message = jsonObject.getString("message");
                temp = jsonObject.getJSONObject("School_Info");
                Log.d("Temp", temp.toString());
                if (success) {
                    Log.d("Success", "login");
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
            mySharedPreferences.setSchoolAddress(temp.getString("school_address"));
            mySharedPreferences.setSchoolMobileNumber(temp.getString("school_contact_number"));
            mySharedPreferences.setSchoolLandlineNumber(temp.getString("school_landline_number"));
            mySharedPreferences.setSchoolEmailId(temp.getString("school_contact_email"));
            mySharedPreferences.setSchoolWebsite(temp.getString("school_website"));
            mySharedPreferences.setSchoolGPlusURL(temp.getString("g_plus_link"));
            mySharedPreferences.setSchoolTwitterURL(temp.getString("twitter_page_link"));
            mySharedPreferences.setSchoolLinkedInURL(temp.getString("linkedIn_page_link"));
            mySharedPreferences.setSchoolFBURL(temp.getString("facbook_page_link"));
            mySharedPreferences.setSchoolInfoStatus(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void callNumber(String mobileNumber) {


        Toast.makeText(getActivity().getApplicationContext(), "Calling", Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String phoneNumber = mobileNumber;
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.school_info_img_g_plus:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mySharedPreferences.getSchoolGPlusURL()));
                startActivity(intent);
                getActivity().overridePendingTransition(
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.school_info_img_fb:
                try {
                    String uri = "fb://page/394492800677531";
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    Log.d("FB", mySharedPreferences.getSchoolFBURL());
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mySharedPreferences.getSchoolFBURL())));
                }

                break;
            case R.id.school_info_img_linkedin:
                try{
                    String linkedInUrl="linkedin://company/"+"";
                }catch (Exception e)
                {

                }
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mySharedPreferences.getSchoolLinkedInURL()));
                startActivity(intent);
                getActivity().overridePendingTransition(
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.school_info_img_twitter:

                try {
                    String twitterUrl="twitter://user?user_id="+"2291070648";
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    twitterUrl));
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }
                catch (Exception e)
                {
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    mySharedPreferences.getSchoolTwitterURL()));
                    startActivity(intent);
                }
                break;

            case R.id.school_info_img_call:
                callNumber(tvMobileNumber.getText().toString().trim());
                break;
            case R.id.school_info_img_call_landline:
                callNumber(tvLandlineNumber.getText().toString().trim());
                break;
        }
    }
}
