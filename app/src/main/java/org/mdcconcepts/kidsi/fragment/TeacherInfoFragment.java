package org.mdcconcepts.kidsi.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;


public class TeacherInfoFragment extends Fragment {
	
	public TeacherInfoFragment(){}

    static  View rootView=null;
    Typeface font;
    TextView TextViewController_TeacherName;
    TextView TextViewController_TeacherInfo;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        try {
             rootView = inflater.inflate(R.layout.fragment_teacher_info, container, false);

            font=Typeface.createFromAsset(getActivity().getAssets(), Util.FontName);

            TextViewController_TeacherName=(TextView)rootView.findViewById(R.id.TextViewController_TeacherName);

            TextViewController_TeacherInfo=(TextView)rootView.findViewById(R.id.TextViewController_TeacherInfo);

            TextViewController_TeacherName.setTypeface(font,Typeface.BOLD);

            TextViewController_TeacherInfo.setTypeface(font,Typeface.BOLD);





        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return rootView;
    }
}
