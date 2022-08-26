package com.app.locxshop.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


//import com.app.locxshop.Ahmedabad;
//import com.app.locxshop.Bangalore;
//import com.app.locxshop.Delhi;
import com.app.locxshop.Ahmedabad;
import com.app.locxshop.Bangalore;
import com.app.locxshop.Delhi;
//import com.app.locxshop.Mumbai;
import com.app.locxshop.SubActivity.FAQs;
import com.app.locxshop.Mumbai;
import com.app.locxshop.R;
import com.app.locxshop.Search;
import com.app.locxshop.SubActivity.AddStoreActivity;
import com.app.locxshop.SubActivity.ArticleActivity;
import com.app.locxshop.SubActivity.FindWorkActivity;
import com.app.locxshop.SubActivity.SettingsActivity;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.app.locxshop.activities.PostAdapter.PostViewAdapter;

import java.util.ArrayList;
import java.util.List;

//implements adapterplace.ListItemClickListener
public class MainActivity extends AppCompatActivity implements adapterplace.ListItemClickListener {

    RecyclerView phoneRecycler, id_recycler;
    RecyclerView.Adapter adapter;

    // Floating Button Object
    FloatingActionButton foot;

    // Firebase Authentication Object
    FirebaseAuth firebaseAuth;

    // DatabaseRefrence Object
    private DatabaseReference dbRef;

    // Nvaigation View Object
    NavigationView navigationView;

    //DrawLayout Object
    DrawerLayout drawerLayout;

    // Toggle Object that appear has 3 bar
    ActionBarDrawerToggle mToggle;

    // Toolbar Object
    Toolbar mToolbar;

    Fragment selectedFragment = null;

    TextView text1, text2;
    ImageView imageView;

    DatabaseReference ref;
    FirebaseUser firebaseUser;

    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);

        firebaseAuth = FirebaseAuth.getInstance();



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Nav user = snapshot.getValue(Nav.class);
                text1.setText(user.getName());
                text2.setText(user.getEmail());
                Glide.with(getApplicationContext()).load(user.getImageurl()).into(imageView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //ToolBar id
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //NavigationView id
        navigationView = (NavigationView) findViewById(R.id.navo);
        View headerView = navigationView.getHeaderView(0);
        text1 = (TextView) headerView.findViewById(R.id.textView8);
        text2 = (TextView) headerView.findViewById(R.id.texe);
        imageView = (ImageView) headerView.findViewById(R.id.imageView3);

        //DrawLayout id
        drawerLayout =(DrawerLayout) findViewById(R.id.draw_in);

        // ImageSlider id
        ImageSlider imageSlider = findViewById(R.id.slider);

        // CardView Recycler id
        phoneRecycler = findViewById((R.id.my_recycler));
        phoneRecycler();

        // How toogle bar will close and open
        mToggle = new ActionBarDrawerToggle(this, drawerLayout,mToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        // this show will text will be black when running
        navigationView.setCheckedItem(R.id.nav_account);

        // Navigation Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment fragment;
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                switch (item.getItemId())
                {
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;

                    case R.id.nav_settings:
                        selectedFragment = null;
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;

                    case R.id.nav_logout:
                        selectedFragment = null;
                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.find_work:
                        selectedFragment = null;
                        startActivity(new Intent(MainActivity.this, FindWorkActivity.class));
                        break;

                    case R.id.search:
                        selectedFragment = null;
                        startActivity(new Intent(MainActivity.this, Search.class));
                        break;

                    case R.id.nav_about_us:
                        selectedFragment=null;
                        startActivity(new Intent(MainActivity.this, AboutUs.class));
                        break;

                    case R.id.globe_article:
                        selectedFragment = null;
                        startActivity(new Intent(MainActivity.this, ArticleActivity.class));
                        break;

                    case R.id.faq:
                        selectedFragment = null;
                        startActivity(new Intent(MainActivity.this, FAQs.class));
                        break;
                }
                return true;
            }

        });

        // ImageSlider Images
        final List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.boutiquesalesassociate, "Boutique Sales Associate"));
        slideModels.add(new SlideModel(R.drawable.bikeshopmechanic, "Bike Shop Mechanic"));
        slideModels.add(new SlideModel(R.drawable.photographer, "Photographer"));
        slideModels.add(new SlideModel(R.drawable.host, "Host or Server at a Restaurant"));


        imageSlider.setImageList(slideModels, true);


        foot = findViewById(R.id.foat);
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);

            }
        });

    }



    // Function phoneRecycler for swipe cardview
    private void phoneRecycler() {
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});
        GradientDrawable gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf,0xff7adccf});

        phoneRecycler.setHasFixedSize(true);
        phoneRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<phonehelper> placeLocation = new ArrayList<phonehelper>();

        placeLocation.add(new phonehelper(R.drawable.image, "Ahmedabad", gradient1));
        placeLocation.add(new phonehelper(R.drawable.image1, "Mumbai", gradient2));
        placeLocation.add(new phonehelper(R.drawable.image2, "Delhi", gradient3));
        placeLocation.add(new phonehelper(R.drawable.image3, "Bangalore", gradient4));

        adapter = new adapterplace(placeLocation, MainActivity.this);
        phoneRecycler.setAdapter(adapter);
    }

    @Override
    public void onphoneListClick(int clickedItemIndex) {
        Intent mIntent;
        switch(clickedItemIndex){
            case 0:
                mIntent = new Intent(MainActivity.this, Ahmedabad.class);
                startActivity(mIntent);
                break;
            case 1:
                mIntent = new Intent(MainActivity.this, Mumbai.class);
                startActivity(mIntent);
                break;
            case 2:
                mIntent = new Intent(MainActivity.this, Delhi.class);
                startActivity(mIntent);
                break;
            case 3:
                mIntent = new Intent(MainActivity.this, Bangalore.class);
                startActivity(mIntent);
                break;

        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


}