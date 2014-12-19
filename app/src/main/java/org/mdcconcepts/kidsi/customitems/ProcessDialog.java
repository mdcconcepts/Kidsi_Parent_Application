package org.mdcconcepts.kidsi.customitems;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;

/**
 * Created by mdcconcepts on 11/7/2014.
 */
public class ProcessDialog extends Dialog{

    Activity _mContext;

    String Title;

    TextView TextViewController_Title;

    Typeface font;

    public ProcessDialog(Activity context,String title) {
        super(context);
        this._mContext=context;
        this.Title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setCancelable(false);

        setContentView(R.layout.dialog_process);

        TextViewController_Title=(TextView)findViewById(R.id.TextViewController_Title);

        font=Typeface.createFromAsset(_mContext.getAssets(), Util.FontName);

        TextViewController_Title.setTypeface(font);

        TextViewController_Title.setText(Title);
    }
}
