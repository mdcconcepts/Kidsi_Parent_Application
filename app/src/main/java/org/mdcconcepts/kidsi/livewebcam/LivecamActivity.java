package org.mdcconcepts.kidsi.livewebcam;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.mdcconcepts.kidsi.R;

public class LivecamActivity extends FragmentActivity {

//    final static String USERNAME = "admin";
//    final static String PASSWORD = "1234";
//    //	final static String RTSP_URL = "rtsp://192.168.100.4/ipcam.sdp";
//    final static String RTSP_URL = "rtsp://192.168.100.3/ipcam.sdp";
//    //	final static String RTSP_URL = "rtsp://115.115.195.42:554/ipcam.sdp";
//    private MediaPlayer _mediaPlayer;
//   // private SurfaceHolder _surfaceHolder;

    static ViewPager mViewPager;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Set up a full-screen black window.


            setContentView(R.layout.activity_livecam);
            mViewPager = (ViewPager) findViewById(R.id.vpPager);
            mViewPager.setPageMargin(20);
            mViewPager.setBackgroundColor(Color.WHITE);
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(adapterViewPager);

            // Configure the view that renders live video.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        /**
         * NUM_ITEMS represents number of pages in viewPager
         */
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment i.e Personal
                    // Information Fragment
                    String USERNAME = "admin";
                    String PASSWORD = "1234";
                    String RTSP_URL = "rtsp://192.168.100.4/ipcam.sdp";
                    CameraOneFragment c = new CameraOneFragment();
                    c.setData(USERNAME, PASSWORD, RTSP_URL);
                    return c.newInstance(0, "Camera 1", USERNAME, PASSWORD, RTSP_URL);
                case 1: // Fragment # 1 - This will show SecondFragment i.e
                    String USERNAME1 = "admin";
                    String PASSWORD1 = "1234";
                    String RTSP_URL1 = "rtsp://192.168.100.3/ipcam.sdp";

                    CameraOneFragment c1 = new CameraOneFragment();
                    c1.setData(USERNAME1, PASSWORD1, RTSP_URL1);
                    return c1.newInstance(0, "Camera 1", USERNAME1, PASSWORD1, RTSP_URL1);


                default:
                    return null;
            }

        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Camera 1";

                case 1:
                    return "Camera 2";


                default:
                    return "Page " + position;
            }

        }

    }

}
