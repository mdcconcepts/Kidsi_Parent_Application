package org.mdcconcepts.kidsi.livewebcam;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.mdcconcepts.kidsi.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 10/30/2014.
 */
public class CameraTwoFragment extends Fragment implements
        MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

    View view;

    final static String USERNAME = "admin";
    final static String PASSWORD = "1234";
    //	final static String RTSP_URL = "rtsp://192.168.100.4/ipcam.sdp";
    final static String RTSP_URL = "rtsp://192.168.100.4/ipcam.sdp";
    //	final static String RTSP_URL = "rtsp://115.115.195.42:554/ipcam.sdp";
    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;

    public static Fragment newInstance(int page, String title) {
        CameraOneFragment fragmentFirst = new CameraOneFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Window window = getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        window.setBackgroundDrawableResource(Color.BLACK);

        view = inflater.inflate(R.layout.fragment_camera_two, container,
                false);

        SurfaceView surfaceView = (SurfaceView)view.findViewById(R.id.surfaceView);
        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        _surfaceHolder.setFixedSize(320, 240);
        return view;
    }
    /*
	 * SurfaceHolder.Callback
	 */

    @Override
    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setDisplay(_surfaceHolder);

        Context context = getActivity();
        Map<String, String> headers = getRtspHeaders();
        Uri source = Uri.parse(RTSP_URL);

        try {
            // Specify the IP camera's URL and auth headers.
            _mediaPlayer.setDataSource(context, source, headers);

            // Begin the process of setting up a video stream.
            _mediaPlayer.setOnPreparedListener(this);
            _mediaPlayer.prepareAsync();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {
        _mediaPlayer.release();
    }

    private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        String basicAuthValue = getBasicAuthValue(USERNAME, PASSWORD);
        headers.put("Authorization", basicAuthValue);
        return headers;
    }

    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }

    /*
     * MediaPlayer.OnPreparedListener
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }
}
