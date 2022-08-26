package com.app.locxshop.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.locxshop.R;

public class WorkDetail extends AppCompatActivity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);

        imageView = (ImageView) findViewById(R.id.image1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int resId = bundle.getInt("PosId");
            imageView.setImageResource(resId);
        }

    }
}