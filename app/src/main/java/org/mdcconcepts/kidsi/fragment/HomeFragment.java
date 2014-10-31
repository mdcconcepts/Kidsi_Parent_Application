package org.mdcconcepts.kidsi.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import org.mdcconcepts.kidsi.R;
import org.mdcconcepts.kidsi.chat.RegisterActivity;
import org.mdcconcepts.kidsi.livewebcam.LivecamActivity;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    static View rootView = null;
    private ImageView cameraImg, healthImage, performanceImage, chatImgage, iconView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            cameraImg = (ImageView) rootView.findViewById(R.id.imageCamera);
            healthImage = (ImageView) rootView.findViewById(R.id.imageHealth);
            performanceImage = (ImageView) rootView.findViewById(R.id.imagePerforme);
            chatImgage = (ImageView) rootView.findViewById(R.id.imageChat);

            iconView = (ImageView) rootView.findViewById(R.id.iconView);
            final int imagesToShow[] = {R.drawable.livecamerabigimg, R.drawable.healthbigimg, R.drawable.performancebigimg, R.drawable.chatbigimg};
            final ImageView imageViewButtonArray[] = {cameraImg, healthImage, performanceImage, chatImgage};

            cameraImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        animate(iconView, cameraImg, imageViewButtonArray, imagesToShow, 0, false);
                        Intent intent = new Intent(getActivity(), LivecamActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                finish();

                    //startActivityForResult(intent, 0);

                    //iconView.setImageResource(R.drawable.camera);


                }
            });
            healthImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    //startActivityForResult(intent, 0);
                    try {
                        animate(iconView, healthImage, imageViewButtonArray, imagesToShow, 1, false);
                        //iconView.setImageResource(R.drawable.camera);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

            performanceImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    //startActivityForResult(intent, 0);
                    try {
                        animate(iconView, performanceImage, imageViewButtonArray, imagesToShow, 2, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //iconView.setImageResource(R.drawable.camera);


                }
            });

            chatImgage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    try {
                        animate(iconView, chatImgage, imageViewButtonArray, imagesToShow, 3, false);

                        Intent intent = new Intent(getActivity(), RegisterActivity.class);

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
        //animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);


        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                // animate(imageView, images, 0,true)

//	            if (images.length - 1 > imageIndex) {
//	                animate(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
//	            }
//	            else {
//	                if (forever == true){
//	                animate(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
//	                }
//	            }
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
