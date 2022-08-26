package com.app.locxshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.locxshop.R;

public class Image2Activity extends AppCompatActivity {

    ImageView imageView;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2);

        imageView = (ImageView) findViewById(R.id.imms);

        if(getIntent().getExtras() != null){
            imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
            imageView.setImageURI(imageUri);
        }
    }
}