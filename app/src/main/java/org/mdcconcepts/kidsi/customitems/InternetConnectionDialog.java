package org.mdcconcepts.kidsi.customitems;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;

/**
 * Created by mdcconcepts on 11/3/2014.
 */
public class InternetConnectionDialog extends Dialog {

    Activity _mContext;

    TextView TextViewController_NoInternet;

    Button ButtonController_Ok;

    Typeface font;

    public InternetConnectionDialog(Activity context) {
        super(context);
        this._mContext=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);

        setContentView(R.layout.no_internet_connection);

        TextViewController_NoInternet=(TextView)findViewById(R.id.TextViewController_NoInternet);

        ButtonController_Ok=(Button)findViewById(R.id.ButtonController_Ok);

        font=Typeface.createFromAsset(_mContext.getAssets(), Util.FontName);

        TextViewController_NoInternet.setTypeface(font);
        ButtonController_Ok.setTypeface(font);
        ButtonController_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
