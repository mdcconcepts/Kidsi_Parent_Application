package org.mdcconcepts.kidsi.fragment;


import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.mdcconcepts.kidsi.performance.PerfomanceChartActivity;
import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.chat.FinalChatActivity;
import org.mdcconcepts.kidsi.health.HealthChartActivity;
import org.mdcconcepts.kidsi.livewebcam.LivecamActivity;


public class HomeFragment extends Fragment implements View.OnClickListener, Animation.AnimationListener {

    public HomeFragment() {
    }

    static View rootView = null;
    Button btnVideoStreaming;
    Button btnHealth;
    Button btnPerformance;
    Button btnChat;
    private FrameLayout dynamicFrameLayout;
    Animation animation;
    LinearLayout layoutRowOne;
    LinearLayout layoutRowTwo;
    LinearLayout layoutRowThree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            initailizeViews();
            setParameters();

            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_bottom_up);
            dynamicFrameLayout.setAnimation(animation);
            animation.setAnimationListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void initailizeViews() {
        dynamicFrameLayout = (FrameLayout) rootView.findViewById(R.id.home_fragment_frameLayout_dynamic);
        btnVideoStreaming = (Button) rootView.findViewById(R.id.Button_Controller_Video_Streaming);
        btnHealth = (Button) rootView.findViewById(R.id.Button_Controller_Health);
        btnChat = (Button) rootView.findViewById(R.id.Button_Controller_Chat);
        btnPerformance = (Button) rootView.findViewById(R.id.Button_Controller_Performance);
        layoutRowOne = (LinearLayout) rootView.findViewById(R.id.fragment_home_row_one);
        layoutRowTwo = (LinearLayout) rootView.findViewById(R.id.fragment_home_row_two);
        layoutRowThree = (LinearLayout) rootView.findViewById(R.id.fragment_home_row_three);
    }

    private void setParameters() {

        btnVideoStreaming.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
        btnChat.setOnClickListener(this);
        btnPerformance.setOnClickListener(this);
    }


//    private void animate(final ImageView imageView, final ImageView imgChange, final ImageView[] imgViewControl, final int images[], final int imageIndex, final boolean forever) {
//
//        //imageView <-- The View which displays the images
//        //images[] <-- Holds R references to the images to display
//        //imageIndex <-- index of the first image to show in images[]
//        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.
//
//        int fadeInDuration = 500; // Configure time values here
//        int timeBetween = 3000;
//        int fadeOutDuration = 500;
//
//        imageView.setVisibility(View.VISIBLE);    //Visible or invisible by default - this will apply when the animation ends
//        imageView.setImageResource(images[imageIndex]);
//
//        for (int i = 0; i < imgViewControl.length; i++) {
//            if (imgChange == imgViewControl[i]) {
//                imgViewControl[i].setAlpha(45);
//            } else
//                imgViewControl[i].setAlpha(255);
//        }
//
//
//        Animation fadeIn = new AlphaAnimation(0, 1);
//        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
//        fadeIn.setDuration(fadeInDuration);
//
//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
//        fadeOut.setStartOffset(fadeInDuration + timeBetween);
//        fadeOut.setDuration(fadeOutDuration);
//
//        AnimationSet animation = new AnimationSet(false); // change to false
//        animation.addAnimation(fadeIn);
//        animation.setRepeatCount(1);
//
//
//        imageView.setAnimation(animation);
//
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            public void onAnimationEnd(Animation animation) {
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//            }
//
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//            }
//        });
//    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.Button_Controller_Chat:
                try {
                    Intent intent = new Intent(getActivity(), FinalChatActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.Button_Controller_Health:
                try {
                    Intent intent = new Intent(getActivity(), HealthChartActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.Button_Controller_Performance:
                try {
                    Intent intent = new Intent(getActivity(), PerfomanceChartActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.Button_Controller_Video_Streaming:
                try {
                    Intent intent = new Intent(getActivity(), LivecamActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(
                            R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {


//                    LinearLayout_One.setVisibility(View.VISIBLE);
//                    Animation icon_animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_icons);
//
//                   LinearLayout_One.setAnimation(icon_animation);

        layoutRowOne.setVisibility(View.VISIBLE);
        layoutRowTwo.setVisibility(View.VISIBLE);
        layoutRowThree.setVisibility(View.VISIBLE);

        ObjectAnimator anim = ObjectAnimator.ofFloat(layoutRowOne, "alpha", 0f, 1f);
        anim.setDuration(1000);
        anim.start();

        anim = ObjectAnimator.ofFloat(layoutRowTwo, "alpha", 0f, 1f);
        anim.setDuration(1000);
        anim.start();

        anim = ObjectAnimator.ofFloat(layoutRowThree, "alpha", 0f, 1f);
        anim.setDuration(1000);
        anim.start();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
