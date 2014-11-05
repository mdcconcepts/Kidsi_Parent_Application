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

public class SchooolInfoFragment extends Fragment {
	
	public SchooolInfoFragment(){}
    static View rootView =null;
    TextView TextViewController_SchoolInfo;
    Typeface font;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        try {
             rootView = inflater.inflate(R.layout.fragment_school_info, container, false);
             TextViewController_SchoolInfo=(TextView)rootView.findViewById(R.id.TextViewController_SchoolInfo);
            font=Typeface.createFromAsset(rootView.getContext().getAssets(), Util.FontName);
            TextViewController_SchoolInfo.setTypeface(font);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
         
        return rootView;
    }
}
