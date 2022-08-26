package com.app.locxshop.Fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.locxshop.R;
import com.app.locxshop.activities.MainActivity;
import com.app.locxshop.activities.adapterplace;
import com.app.locxshop.activities.phonehelper;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;

import static com.app.locxshop.R.drawable.backwh;
import static com.app.locxshop.R.drawable.image;
import static com.app.locxshop.R.drawable.image1;
import static com.app.locxshop.R.drawable.image2;
import static com.app.locxshop.R.drawable.image3;


public class JobFragment extends Fragment  {

    // SliderImage object
    private SliderLayout sliderLayout;
    RecyclerView phoneRecycler;
    RecyclerView.Adapter adapter ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        // Finding the id
        sliderLayout = view.findViewById(R.id.slider);


        // Setting the Slider Animation and Auto-Swipe time
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2);

        // Function for Slider view and there is swipe function down below setSliderView
        setSliderView();

        // Swipe Card View find id
        phoneRecycler = view.findViewById((R.id.my_recycler));
//        phoneRecycler();

        return view;

    }

    // Creating SwipeView Function
    private void setSliderView(){
        for(int i=0; i<5; i++){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            switch (i){
                case 0:
                    sliderView.setImageDrawable(image);
                    break;
                case 1:
                    sliderView.setImageDrawable(image1);
                    break;
                case 2:
                    sliderView.setImageDrawable(image2);
                    break;
                case 3:
                    sliderView.setImageDrawable(image3);
                    break;

            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER);
            sliderLayout.addSliderView(sliderView);
        }
    }

//    private void phoneRecycler() {
//        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
//        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
//        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
//        GradientDrawable gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
//
//        phoneRecycler.setHasFixedSize(true);
//        phoneRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
//
//        ArrayList<phonehelper> placeLocation = new ArrayList<phonehelper>();
//
//        placeLocation.add(new phonehelper(R.drawable.images, "Ahmedabad", gradient1));
//        placeLocation.add(new phonehelper(R.drawable.image2, "Mumbai", gradient2));
//        placeLocation.add(new phonehelper(R.drawable.image2, "Delhi", gradient3));
//        placeLocation.add(new phonehelper(R.drawable.images, "Bangalore", gradient4));
//
//        adapter = new adapterplace(placeLocation,this);
//        phoneRecycler.setAdapter(adapter);
//    }




}