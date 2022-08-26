package com.app.locxshop.activities;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class phonehelper {

    int image;
    String title;
    GradientDrawable color;


    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public GradientDrawable getColor() {
        return color;
    }

    public phonehelper(int image, String title, GradientDrawable color){
        this.image = image;
        this.title = title;
        this.color = color;
    }


    public Drawable getgrandient() {
        return color;
    }
}
