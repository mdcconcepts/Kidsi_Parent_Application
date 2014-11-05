package org.mdcconcepts.kidsi.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.mdcconcepts.kidsi.R;

/**
 * Created by mdcconcepts on 10/31/2014.
 */
public class FinalChatActivity extends Activity {

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_chat);
        String[] parentName = {
                "Mrs.Poonam",
        };

        String[] parentMobileNumber = {
                "9175018317",
        };

        int[] imageParent = {
                R.drawable.techer_pic,
        };
        grid = (GridView) findViewById(R.id.grid);
        ChatLayoutAdapter adapter = new ChatLayoutAdapter(FinalChatActivity.this, parentName, parentMobileNumber, imageParent);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // DO something
                Intent i = new Intent(FinalChatActivity.this, ChatRoomActivity.class);
                startActivity(i);

            }
        });

        getActionBar().setTitle("Chat Room");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
