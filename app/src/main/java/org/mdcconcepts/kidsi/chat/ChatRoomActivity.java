package org.mdcconcepts.kidsi.chat;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;

/**
 * Created by mdcconcepts on 11/1/2014.
 */
public class ChatRoomActivity extends Activity {

    EditText EditTextViewController_ChatMsg;

    TextView TextViewController_Msg_One;

    TextView TextViewController_Msg_Two;

    TextView TextViewController_Msg_Three;

    TextView TextViewController_Msg_Dynamic;

    FrameLayout FrameLayout_Parent_Dynamic;

    ImageView ImageController_Send;

    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room);

        EditTextViewController_ChatMsg=(EditText)findViewById(R.id.EditTextViewController_ChatMsg);

        TextViewController_Msg_One=(TextView)findViewById(R.id.TextViewController_Msg_One);

        TextViewController_Msg_Two=(TextView)findViewById(R.id.TextViewController_Msg_Two);

        TextViewController_Msg_Three=(TextView)findViewById(R.id.TextViewController_Msg_Three);

        TextViewController_Msg_Dynamic=(TextView)findViewById(R.id.TextViewController_Msg_Dynamic);


        font=Typeface.createFromAsset(getAssets(), Util.FontName);

        TextViewController_Msg_One.setTypeface(font);

        TextViewController_Msg_Two.setTypeface(font);

        TextViewController_Msg_Three.setTypeface(font);

        EditTextViewController_ChatMsg.setTypeface(font);

        TextViewController_Msg_Dynamic.setTypeface(font);


        FrameLayout_Parent_Dynamic=(FrameLayout)findViewById(R.id.FrameLayout_Parent_Dynamic);

        ImageController_Send=(ImageView)findViewById(R.id.ImageController_Send);

        ImageController_Send.setVisibility(View.GONE);

        ImageController_Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

//                Toast.makeText(getActivity(),
//                        "ImageButton (selector) is clicked!",
//                        Toast.LENGTH_SHORT).show();
                try {
//                        animate(iconView, performanceImage, imageViewButtonArray, imagesToShow, 2, false);

                    String ChatMessage=EditTextViewController_ChatMsg.getText().toString().trim();
                    if(!ChatMessage.isEmpty())
                    {
                        TextViewController_Msg_Dynamic.setText(ChatMessage);
                        FrameLayout_Parent_Dynamic.setVisibility(View.VISIBLE);
                        EditTextViewController_ChatMsg.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        EditTextViewController_ChatMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()>0)
                {
                    ImageController_Send.setVisibility(View.VISIBLE);
                }
                else
                {

                    ImageController_Send.setVisibility(View.GONE);
                }
            }
        });



        getActionBar().setHomeButtonEnabled(true);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Mrs.Poonam");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                // NavUtils.navigateUpFromSameTask(this);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
