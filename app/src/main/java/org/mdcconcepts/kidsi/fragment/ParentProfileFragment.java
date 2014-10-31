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

public class ParentProfileFragment extends Fragment {
	
	public ParentProfileFragment(){}
    static View rootView =null;
    Typeface font;
    TextView TextViewController_ParentName;
    TextView TextViewController_ParentInfo;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        try {
             rootView = inflater.inflate(R.layout.fragment_parent_profile, container, false);
             TextViewController_ParentName=(TextView)rootView.findViewById(R.id.TextViewController_ParentName);
            TextViewController_ParentInfo=(TextView)rootView.findViewById(R.id.TextViewController_ParentInfo);

            font=Typeface.createFromAsset(rootView.getContext().getAssets(), Util.FontName);
            TextViewController_ParentName.setTypeface(font,Typeface.BOLD);
            TextViewController_ParentInfo.setTypeface(font);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return rootView;
    }
}
