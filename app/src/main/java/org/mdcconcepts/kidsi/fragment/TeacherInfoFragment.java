package org.mdcconcepts.kidsi.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.chat.ChatRoomActivity;


public class TeacherInfoFragment extends Fragment {

    public TeacherInfoFragment() {
    }

    static View rootView = null;
    Typeface font;

    TextView TextViewController_TeacherName;

    TextView TextViewController_TeacherInfo;

    TextView TextViewController_PhoneTitle;

    TextView TextViewController_PhoneNumber;

    TextView TextViewController_TeacherInfo_Title;

    ImageView ImageView_Call;

    ImageView ImageViewController_CreateMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_teacher_info, container, false);

            font = Typeface.createFromAsset(getActivity().getAssets(), Util.FontName);

            TextViewController_TeacherName = (TextView) rootView.findViewById(R.id.TextViewController_TeacherName);

            TextViewController_TeacherInfo = (TextView) rootView.findViewById(R.id.TextViewController_TeacherInfo);

            TextViewController_PhoneTitle= (TextView) rootView.findViewById(R.id.TextViewController_PhoneTitle);

            TextViewController_PhoneNumber= (TextView) rootView.findViewById(R.id.TextViewController_PhoneNumber);

            TextViewController_TeacherInfo_Title = (TextView) rootView.findViewById(R.id.TextViewController_TeacherInfo_Title);


            TextViewController_TeacherName.setTypeface(font);

            TextViewController_TeacherInfo.setTypeface(font);

            TextViewController_PhoneTitle.setTypeface(font);

            TextViewController_PhoneNumber.setTypeface(font);

            TextViewController_TeacherInfo_Title.setTypeface(font);


            ImageView_Call = (ImageView) rootView.findViewById(R.id.ImageView_Call);

            ImageViewController_CreateMsg=(ImageView)rootView.findViewById(R.id.ImageViewController_CreateMsg);

            ImageViewController_CreateMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(getActivity(), ChatRoomActivity.class);
                    startActivity(i);
//                    getActivity().finish();
                }
            });

            ImageView_Call.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

//                    Toast.makeText(getActivity(),
//                            "ImageButton (selector) is clicked!",
//                            Toast.LENGTH_SHORT).show();
                    try {
//                        animate(iconView, chatImgage, imageViewButtonArray, imagesToShow, 3, false);

//                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
                        callNumber();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

            TextViewController_PhoneNumber.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

//                    Toast.makeText(getActivity(),
//                            "ImageButton (selector) is clicked!",
//                            Toast.LENGTH_SHORT).show();
                    try {
//                        animate(iconView, chatImgage, imageViewButtonArray, imagesToShow, 3, false);

//                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
                        callNumber();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void callNumber() {


        Toast.makeText(getActivity().getApplicationContext(), "Calling", Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String phoneNumber = "9975239423";
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }


}
