package com.app.locxshop.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.locxshop.R;
import com.app.locxshop.activities.MainActivity;

public class AddStoreActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        imageView = findViewById(R.id.backarrow);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}