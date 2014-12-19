package org.mdcconcepts.kidsi.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.mdcconcepts.kidsi.performance.PerfomanceChartActivity;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.Util.Util;
import org.mdcconcepts.kidsi.chat.FinalChatActivity;
import org.mdcconcepts.kidsi.health.HealthChartActivity;
import org.mdcconcepts.kidsi.livewebcam.LivecamActivity;


public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    static View rootView = null;
    Button btnVideoStreaming;
    Button btnHealth;
    Button btnPerformance;
    Button btnChat;
    Typeface font;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.fragment_home, container, false);

            font = Typeface.createFromAsset(getActivity().getAssets(), Util.FontName);

            btnVideoStreaming = (Button) rootView.findViewById(R.id.Button_Controller_Video_Streaming);

            btnVideoStreaming.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    try {
                        Intent intent = new Intent(getActivity(), LivecamActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });
            btnHealth = (Button) rootView.findViewById(R.id.Button_Controller_Health);

            btnHealth.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    try {
                        Intent intent = new Intent(getActivity(), HealthChartActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });
            btnPerformance = (Button) rootView.findViewById(R.id.Button_Controller_Performance);

            btnPerformance.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    try {
                        Intent intent = new Intent(getActivity(), PerfomanceChartActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });
            btnChat = (Button) rootView.findViewById(R.id.Button_Controller_Chat);

            btnChat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    try {
                        Intent intent = new Intent(getActivity(), FinalChatActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void animate(final ImageView imageView, final ImageView imgChange, final ImageView[] imgViewControl, final int images[], final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 500;

        imageView.setVisibility(View.VISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        for (int i = 0; i < imgViewControl.length; i++) {
            if (imgChange == imgViewControl[i]) {
                imgViewControl[i].setAlpha(45);
            } else
                imgViewControl[i].setAlpha(255);
        }


        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.setRepeatCount(1);


        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }
}
