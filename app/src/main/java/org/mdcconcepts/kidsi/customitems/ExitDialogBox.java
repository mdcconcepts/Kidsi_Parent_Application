package org.mdcconcepts.kidsi.customitems;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.mdcconcepts.kidsi.LoginActivity;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.AppSharedPreferences;
import org.mdcconcepts.kidsi.Util.Util;

/**
 * Created by mdcconcepts on 11/3/2014.
 */
public class ExitDialogBox extends Dialog {

    Button btnYes;
    Button btnNo;
    Typeface font;
    Activity mContext;
    Intent intent;
    private AppSharedPreferences mySharedPreferences;
    TextView TextViewController_Exit;

    public ExitDialogBox(Activity context) {

        super(context);
        this.mContext=context;
//        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(false);

        setContentView(R.layout.dialog_exit);

        mySharedPreferences = new AppSharedPreferences(mContext);

        btnYes=(Button)findViewById(R.id.ButtonController_Yes);

        btnNo =(Button)findViewById(R.id.ButtonController_No);

        TextViewController_Exit=(TextView)findViewById(R.id.TextViewController_Exit);

        font=Typeface.createFromAsset(mContext.getAssets(), Util.FontName);

        btnYes.setTypeface(font);

        btnNo.setTypeface(font);

        TextViewController_Exit.setTypeface(font);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySharedPreferences.clearAllSharedPreferences();
                dismiss();

                intent=new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);

                mContext.finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}
