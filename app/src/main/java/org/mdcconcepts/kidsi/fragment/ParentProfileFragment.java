package org.mdcconcepts.kidsi.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.customitems.CircularImageView;
import org.mdcconcepts.kidsi.customitems.CompleteAsyncTask;
import org.mdcconcepts.kidsi.customitems.ConnectionDetector;
import org.mdcconcepts.kidsi.customitems.CustomTextView;
import org.mdcconcepts.kidsi.customitems.CustomValidator;
import org.mdcconcepts.kidsi.customitems.InternetConnectionDialog;
import org.mdcconcepts.kidsi.customitems.KidListAdapter;
import org.mdcconcepts.kidsi.customitems.UniversalAsynckTask;
import org.mdcconcepts.kidsi.customitems.UploadPicInterface;
import org.mdcconcepts.kidsi.customitems.UploadProfilePictureTask;
import org.mdcconcepts.kidsi.kids.KidsProfileActivity;

/**
 * Author: Pallavi Udawant
 * This fragment displays the Parents profile.
 * All details of the Parent and list of his kids.
 */
public class ParentProfileFragment extends Fragment implements CompleteAsyncTask, AdapterView.OnItemClickListener, View.OnClickListener, UploadPicInterface {

    public ParentProfileFragment() {
    }

    static View rootView = null;

    private CustomTextView tvParentName;
    private CustomTextView tvParentAddress;
    private CustomTextView tvParentPrimaryMobNo;
    private CustomTextView tvParentEmergancyNo;
    private CustomTextView tvParentComapnyNo;
    private CustomTextView tvParentEmailAddress;

    private UniversalAsynckTask universalAsynckTask;
    private Boolean success;
    private String message;
    private JSONObject temp;
    private AppSharedPreferences mySharedPreferences;
    private Boolean isConnected;
    private ConnectionDetector connectionDetector;
    private ListView lvKids;
    private KidListAdapter kidListAdapter;
    private Intent intent;

    private JSONArray kidsJsonArray;
    private ListView lvMain;
    private View lvHeader;
    private ImageView imgEdit;
    private Button btnSave;
    private EditText edtAddress;
    private EditText edtEmergencyNo;
    private EditText edtMobileNumber;
    private EditText edtCompanyNumber;
    private EditText edtEmailId;
    private Typeface font;
    private int flag;
    private String fileName;

    private String parentName;
    private String parentAddress;
    private String parentMobileNumber;
    private String parentCompanyNo;
    private String parentEmergencyNo;
    private String parentEmailId;

    private CircularImageView imgParentprofile;
    private Intent galleryIntent;

    private int GET_PARENT_INFO_FLAG = 1;
    private int GET_KID_INFO_FLAG = 2;

    private int KID_DETAILS_REQUEST = 2;
    private int OPEN_GALLERY_REQUEST = 1;

    InternetConnectionDialog connectionDialog;
    UploadProfilePictureTask uploadProfilePictureTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.fragment_parent_profile, container, false);
            lvMain = (ListView) rootView.findViewById(R.id.parent_profile_lv_main);
            if (lvHeader == null) {
                lvHeader = inflater.inflate(R.layout.parent_profile, null);
                lvMain.addHeaderView(lvHeader, null, false);
            }

            initailizeParameters();
            setParameters();
            setFont();
            isConnected = connectionDetector.isConnectingToInternet();

            if (isConnected) {
//
                String[] requestParameters = new String[2];
                requestParameters[0] = Util.PARENT_INFO_PROFILE;
                requestParameters[1] = getRequestParameters().toString();

                universalAsynckTask = new UniversalAsynckTask(ParentProfileFragment.this, "Loading", getActivity(), GET_PARENT_INFO_FLAG);
                universalAsynckTask.execute(requestParameters);

            } else {
                connectionDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void initailizeParameters() {

        tvParentName = (CustomTextView) lvHeader.findViewById(R.id.parent_profile_textview_name);
        tvParentAddress = (CustomTextView) lvHeader.findViewById(R.id.parent_profile_textview_addr);
        tvParentPrimaryMobNo = (CustomTextView) lvHeader.findViewById(R.id.parent_profile_textview_mobile_number);
        tvParentEmergancyNo = (CustomTextView) lvHeader.findViewById(R.id.teacher_profile_textview_emergancy_number);
        tvParentComapnyNo = (CustomTextView) lvHeader.findViewById(R.id.parent_profile_textview_company_no);
        tvParentEmailAddress = (CustomTextView) lvHeader.findViewById(R.id.parent_profile_textview_email_addrs);


        imgEdit = (ImageView) lvHeader.findViewById(R.id.parent_profile_img_edit);
        btnSave = (Button) lvHeader.findViewById(R.id.parent_profile_btn_done_edit);
        edtAddress = (EditText) lvHeader.findViewById(R.id.parent_profile_edt_addr);
        edtEmergencyNo = (EditText) lvHeader.findViewById(R.id.parent_profile_edt_emergency_no);
        edtMobileNumber = (EditText) lvHeader.findViewById(R.id.parent_profile_edt_mobile_number);
        edtCompanyNumber = (EditText) lvHeader.findViewById(R.id.parent_profile_edt_company_no);
        edtEmailId = (EditText) lvHeader.findViewById(R.id.parent_profile_edt_email_addrs);
        imgParentprofile = (CircularImageView) lvHeader.findViewById(R.id.parent_profile_img_profile_pic);

        connectionDetector = new ConnectionDetector(getActivity());
        mySharedPreferences = new AppSharedPreferences(getActivity());

        connectionDialog = new InternetConnectionDialog(getActivity());

        font = Typeface.createFromAsset(getActivity().getAssets(), Util.FontName);

        lvMain.setOnItemClickListener(this);
        imgEdit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        imgParentprofile.setOnClickListener(this);


    }

    private void setFont() {
        btnSave.setTypeface(font);
        edtEmergencyNo.setTypeface(font);
        edtMobileNumber.setTypeface(font);
        edtCompanyNumber.setTypeface(font);
        edtAddress.setTypeface(font);
        edtEmailId.setTypeface(font);

    }

    private void setParameters() {
        if (mySharedPreferences.getParentName() != null)
            tvParentName.setText(mySharedPreferences.getParentName());
        else
            tvParentName.setText("Information Not Available");

        if (mySharedPreferences.getParentAddress() != null)
            tvParentAddress.setText(mySharedPreferences.getParentAddress());
        else
            tvParentAddress.setText("Information Not Available");

        if (mySharedPreferences.getParentEmergancyMobileNumber() != null)
            tvParentEmergancyNo.setText(mySharedPreferences.getParentEmergancyMobileNumber());
        else
            tvParentEmergancyNo.setText("Information Not Available");

        if (mySharedPreferences.getParentPrimaryMobileNumber() != null)
            tvParentPrimaryMobNo.setText(mySharedPreferences.getParentPrimaryMobileNumber());
        else
            tvParentPrimaryMobNo.setText("Information Not Available");

        if (mySharedPreferences.getParentCompanyMobileNumber() != null)
            tvParentComapnyNo.setText(mySharedPreferences.getParentCompanyMobileNumber());
        else
            tvParentComapnyNo.setText("Information Not Available");

        if (mySharedPreferences.getParentEmailAddr() != null)
            tvParentEmailAddress.setText(mySharedPreferences.getParentEmailAddr());
        else
            tvParentEmailAddress.setText("Information Not Available");

//        if (mySharedPreferences.getKidJsonString() != null) {
//            try {
//                kidsJsonArray = new JSONArray(mySharedPreferences.getKidJsonString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            kidListAdapter = new KidListAdapter(getActivity(), kidsJsonArray);
//            lvMain.setAdapter(kidListAdapter);
//        }
    }

    @Override
    public void onResume() {
//
//        isConnected = connectionDetector.isConnectingToInternet();
//
//        if (isConnected) {
////
//            String[] requestParameters = new String[2];
//            requestParameters[0] = Util.PARENT_INFO_PROFILE;
//            requestParameters[1] = getRequestParameters().toString();
//
//            universalAsynckTask = new UniversalAsynckTask(ParentProfileFragment.this, "Loading", getActivity(), GET_PARENT_INFO_FLAG);
//            universalAsynckTask.execute(requestParameters);
//
//        } else {
//            connectionDialog.show();
//        }
//        lvMain.invalidate();
        super.onResume();
    }

    private void setParentProfilePic() {
        try {
            Picasso.with(getActivity()).load(mySharedPreferences.getParentProfileUrl())
//                    .resize(100, 100)
                    .error(R.drawable.parent_pic)
                    .into(imgParentprofile);
        } catch (Exception e) {
            imgParentprofile.setImageResource(R.drawable.parent_pic);
            e.printStackTrace();
        }
    }

    private void setMySharedPreferences(JSONObject temp) {
        try {
            mySharedPreferences.setParentName(temp.getString("full_name"));
            mySharedPreferences.setParentAddress(temp.getString("Address"));
            mySharedPreferences.setParentEmergancyMobileNumber(temp.getString("Emergancy_Mobile_Number"));
            mySharedPreferences.setParentPrimaryMobileNumber(temp.getString("Primary_Mobile_Number"));
            mySharedPreferences.setParentCompanyMobileNumber(temp.getString("Landline_Number"));
            mySharedPreferences.setParentEmailAddr(temp.getString("Email"));
            mySharedPreferences.setParentInfoStatus(true);
            mySharedPreferences.setParentProfileUrl(temp.getString("Profile_image"));

            mySharedPreferences.setKidJsonString(temp.getJSONArray("Child_Info").toString());
            setParentProfilePic();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONObject getRequestParameters() {
        JSONObject params = new JSONObject();
        try {
            params.put("parent_id", mySharedPreferences.getParentId());
            Log.d("params", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }


    @Override
    public void onCompleteTask(JSONObject jsonObject, int flag) {

        switch (flag) {
            case 1:
                // This code will be executed after getting the Parent Info
                if (jsonObject != null) {
                    Log.d("Parent Info Fragment", jsonObject.toString());
                    try {
                        success = jsonObject.getBoolean("success");


                        if (success) {
                            temp = jsonObject.getJSONObject("parent_info");
                            kidsJsonArray = temp.getJSONArray("Child_Info");

//                       String i=String.valueOf(kidsJsonArray.length());
                            Log.d("Temp", kidsJsonArray.toString());


                            setMySharedPreferences(temp);
                            setParameters();
                            kidListAdapter = new KidListAdapter(getActivity(), kidsJsonArray);
                            lvMain.setAdapter(kidListAdapter);
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
                break;
            case 2:
                if (jsonObject != null) {

                    Log.d("Update Parent Info", jsonObject.toString());

                    try {
                        success = jsonObject.getBoolean("success");
                        message = jsonObject.getString("message");


                        if (success) {

                            mySharedPreferences.setParentEmergancyMobileNumber(parentEmergencyNo);
                            mySharedPreferences.setParentCompanyMobileNumber(parentCompanyNo);
                            mySharedPreferences.setParentPrimaryMobileNumber(parentMobileNumber);
                            mySharedPreferences.setParentAddress(parentAddress);
                            mySharedPreferences.setParentEmailAddr(parentEmailId);
//TODO save
                            setParameters();
                            imgEdit.setVisibility(View.VISIBLE);
                            btnSave.setVisibility(View.GONE);
                            ediTextVisibility(View.GONE);
                            textViewVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (jsonObject != null) {
                    Log.d("Parent Info Fragment", jsonObject.toString());
                }
                break;


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        //TODO StartActivity for result

        intent = new Intent(getActivity(), KidsProfileActivity.class);
        try {

            intent.putExtra("Kid_Info", kidsJsonArray.getJSONObject(position - 1).toString());
            Log.d("Listview onclick", String.valueOf(position));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivityForResult(intent, KID_DETAILS_REQUEST);
        getActivity().overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parent_profile_img_edit:
                imgEdit.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                ediTextVisibility(View.VISIBLE);
                textViewVisibility(View.GONE);
                setContent();
                break;

            case R.id.parent_profile_btn_done_edit:
                parentName = tvParentName.getText().toString();
                parentAddress = edtAddress.getText().toString();
                parentCompanyNo = edtCompanyNumber.getText().toString();
                parentMobileNumber = edtMobileNumber.getText().toString();
                parentEmergencyNo = edtEmergencyNo.getText().toString();
                parentEmailId = edtEmailId.getText().toString();

                isConnected = connectionDetector.isConnectingToInternet();
                if (isConnected) {
                    if (parentAddress.isEmpty()) {
                        edtAddress.setError("Please Enter Address");
                    } else if (parentEmergencyNo.isEmpty()) {
                        edtEmergencyNo.setError("Please Enter Mobile Number");
                    } else if (!CustomValidator.isValidPhoneNumber(parentEmergencyNo)) {
                        edtEmergencyNo.setError("Invalid Mobile Number");
                    } else if (parentMobileNumber.isEmpty()) {
                        edtMobileNumber.setError("Please Enter Mobile Number");
                    } else if (!CustomValidator.isValidPhoneNumber(parentMobileNumber)) {
                        edtMobileNumber.setError("Invalid Mobile Number");
                    } else if (parentEmailId.isEmpty()) {
                        edtEmailId.setError("Please Enter Email Id");
                    } else if (!CustomValidator.isValidEmail(parentEmailId)) {
                        edtEmailId.setError("Invalid Email Id");
                    } else {
                        String[] requestParameters = new String[2];
                        requestParameters[0] = Util.UPDATE_PARENT_INFO;
                        requestParameters[1] = getParentInfoParams().toString();

                        universalAsynckTask = new UniversalAsynckTask(ParentProfileFragment.this, "Loading", getActivity(), GET_KID_INFO_FLAG);
                        universalAsynckTask.execute(requestParameters);
                        break;
                    }
                } else {
                    connectionDialog.show();
                }

            case R.id.parent_profile_img_profile_pic:
//                Toast.makeText(getActivity(), "Image OnClick", Toast.LENGTH_LONG).show();
                openGallery();
                break;
        }
    }

    /**
     * This method is written to open the gallery
     * setType specifies the type of file that you want to open
     * since here we want to upload an image so we have set type to image.
     */
    private void openGallery() {

        galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, OPEN_GALLERY_REQUEST);
        getActivity().overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);

        switch (requestCode) {
            case 1:
                //This code will be executed after opening the gallery and uploads the selected profile picture
                if (resultCode == Activity.RESULT_OK) {

                    Uri selectedImage = returnedIntent.getData();
//                    imgParentprofile.setImageURI(selectedImage);
                    String filePath = Util.getPath(selectedImage, getActivity());
                    uploadProfilePictureTask = new UploadProfilePictureTask(this, getActivity());
                    uploadProfilePictureTask.execute(filePath, mySharedPreferences.getParentId(), Util.UPDATE_PARENT_PROFILE_PIC);
                }
                break;
            case 2:
                //This code will be executed if user returns back after updating kid profile picture and updates the UI.
                String jsonResponse = returnedIntent.getStringExtra("profile_url");

                Log.d("jsonResponse", jsonResponse);


                break;
        }
    }

    private JSONObject getParentInfoParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("Parent_id", mySharedPreferences.getParentId());
            params.put("full_name", parentName);
            params.put("Address", parentAddress);
            params.put("Primary_Mobile_Number", parentMobileNumber);
            params.put("Emergancy_Mobile_Number", parentEmergencyNo);
            params.put("Landline_Number", parentCompanyNo);
            params.put("Email", parentEmailId);

            Log.d("params", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

    private void ediTextVisibility(int visibility) {
        edtAddress.setVisibility(visibility);
        edtEmergencyNo.setVisibility(visibility);
        edtEmailId.setVisibility(visibility);
        edtMobileNumber.setVisibility(visibility);
        edtCompanyNumber.setVisibility(visibility);
    }

    private void textViewVisibility(int visibility) {
        tvParentAddress.setVisibility(visibility);
        tvParentComapnyNo.setVisibility(visibility);
        tvParentPrimaryMobNo.setVisibility(visibility);
        tvParentEmergancyNo.setVisibility(visibility);
        tvParentEmailAddress.setVisibility(visibility);
    }

    private void setContent() {
        edtAddress.setText(tvParentAddress.getText().toString());
        edtCompanyNumber.setText(tvParentComapnyNo.getText().toString());
        edtMobileNumber.setText(tvParentPrimaryMobNo.getText().toString());
        edtEmailId.setText(tvParentEmailAddress.getText().toString());
        edtEmergencyNo.setText(tvParentEmergancyNo.getText().toString());
    }

    @Override
    public void onUploadComplete(JSONObject jsonObject) {
        if (jsonObject != null) {
            Log.d("OnComplete Upload", jsonObject.toString());
            try {
                if (jsonObject.getBoolean("success")) {
                    mySharedPreferences.setParentProfileUrl(jsonObject.getString("profile_image"));
                    setParentProfilePic();
                } else
                    Toast.makeText(getActivity(), "Upload failed..", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
