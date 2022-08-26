package com.app.locxshop.SubActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.locxshop.R;
import com.app.locxshop.activities.Image2Activity;
import com.app.locxshop.activities.MainActivity;
import com.app.locxshop.activities.PostActivity;
import com.theartofdev.edmodo.cropper.CropImage;

public class ArticleActivity extends AppCompatActivity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        imageView = (ImageView) findViewById(R.id.backarrow);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}