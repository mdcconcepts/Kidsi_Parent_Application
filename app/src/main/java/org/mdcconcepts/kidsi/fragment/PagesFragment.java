package org.mdcconcepts.kidsi.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mdcconcepts.kidsi.R;

public class PagesFragment extends Fragment {
	
	public PagesFragment(){}

    static View rootView=null;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    try {
        rootView = inflater.inflate(R.layout.fragment_pages, container, false);
     }
    catch (Exception e)
     {

    }
        return rootView;
    }
}
