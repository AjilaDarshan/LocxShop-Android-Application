package com.app.locxshop.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.locxshop.JustJava.City;
import com.app.locxshop.JustJava.Radio;
import com.app.locxshop.JustJava.State;
import com.app.locxshop.R;
import com.app.locxshop.SubActivity.FindWorkActivity;
import com.app.locxshop.SubActivity.WorkDetail;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URL;
import java.util.HashMap;

import static com.app.locxshop.R.id.photos;

public class PostActivity extends AppCompatActivity {

    EditText sName, oName, pDesc, sLoc, contact;
    ImageView sImg, close;
    TextView post;
    RadioButton full, part;
    Radio radio;
    State state;
    City city;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1;



    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        sName = findViewById(R.id.sname);
        oName = findViewById(R.id.oname);
        pDesc = findViewById(R.id.desc);
        sLoc = findViewById(R.id.desc);
        sImg = findViewById(photos);
        close = findViewById(R.id.close);
        post = findViewById(R.id.post);
        full = findViewById(R.id.fulltime);
        part = findViewById(R.id.parttime);
        contact = findViewById(R.id.onum);
        radio = new Radio();
        autoCompleteTextView = findViewById(R.id.auto);
        autoCompleteTextView1 = findViewById(R.id.city);
        state = new State();
        city = new City();



        String [] option = {"Andhra Pradesh","Arunachal Pradesh", "Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        String [] option1 = {"Hyderabad","Vijayawada","Guntur","Itanagar", "Tawang", "Bomdila","Hyderabad",
                "Vijayawada",
                "Guntur",
                "Itanagar",
                "Tawang",
                "Bomdila",
                "Dispur",
                "jorhat",
                "sivasagar",
                "Patna",
                "bihar sharif",
                "arrah",
                "Raipur",
                "Bhilai",
                "Bilaspur",
                "Panaji",
                "Pernem",
                "Ponda",
                "Gandhinagar",
                "Ahmedabad",
                "Surat",
                "Chandigarh",
                "Faridabad",
                "Gurugram",
                "Shimla",
                "Dharamsala",
                "Solan",
                "Ranchi",
                "Dhanbad",
                "Jamshedpur",
                "Bengaluru",
                "Bagalkot",
                "Belagavi",
                "Thiruvananthapuram",
                "Kozhikode",
                "Kochi",
                "Bhopal",
                "Indore",
                "Jabalpur",
                "Mumbai",
                "PMC, Pune",
                "Nagpur",
                "Imphal",
                "Bishnupur",
                "Chandel",
                "Shillong",
                "Shillong Cantonment",
                "Tura",
                "Aizawl",
                "Bairabi",
                "Biate",
                "Kohima",
                "Dimapur",
                "Mokokchung",
                "Bhubaneswar",
                "Cuttack",
                "Rourkela",
                "Chandigarh",
                "Ludhiana",
                "Amritsar",
                "Jaipur",
                "Jodhpur",
                "Kota",
                "Gangtok",
                "Pelling",
                "Lachung",
                "Chennai",
                "Coimbatore",
                "Madurai",
                "Hyderabad",
                "Siddipet",
                "Sangareddy",
                "Agartala",
                "Dharmanagar",
                "Kailasahar",
                "Lucknow",
                "Kanpur",
                "Faizabad",
                "Dehradun",
                "Gairsain",
                "Haridwar",
                "Kolkata",
                "Asansol",
                "Siliguri",
        "Dispur", "jorhat", "sivasagar", "Thane"};
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.dropdown_item, option1);
        autoCompleteTextView1.setText(arrayAdapter1.getItem(0).toString(), false);
        autoCompleteTextView1.setAdapter(arrayAdapter1);




        storageReference = FirebaseStorage.getInstance().getReference("Posts");



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));

            }
        });

        sImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postImage();
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }

    private void postImage(){
        CropImage.activity()
                .setAspectRatio(380,200)
                .setMaxCropResultSize(380, 200)
                .start(PostActivity.this);

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){

        final ProgressDialog progressDialog = new ProgressDialog(PostActivity.this);
        progressDialog.setMessage("Posting.....");
        progressDialog.show();

        if(imageUri != null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
            +"."+ getFileExtension(imageUri));

            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isComplete()){
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("postimage", myUrl);
                        hashMap.put("owner", oName.getText().toString());
                        hashMap.put("location", sLoc.getText().toString());
                        hashMap.put("shop", sName.getText().toString());
                        hashMap.put("contact", contact.getText().toString());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        String m1 = (String) hashMap.put("work_time", full.getText().toString());
                        String m2 = (String) hashMap.put("work_time", part.getText().toString());
                        String m3 = (String) hashMap.put("state", autoCompleteTextView.getText().toString());
                        String m4 = (String) hashMap.put("city", autoCompleteTextView1.getText().toString());

                        if (autoCompleteTextView1.isSelected()){
                            city.setCity(m4);
                        }else{
                            Toast.makeText(PostActivity.this, "Select the city", Toast.LENGTH_SHORT).show();
                        }

                        if (autoCompleteTextView.isSelected()){
                            state.setState(m3);
                        }else{
                            Toast.makeText(PostActivity.this, "Select the State", Toast.LENGTH_SHORT).show();
                        }

                        if (full.isChecked()){
                            radio.setTime(m1);

                        }
                        else {
                            radio.setTime(m2);
                        }

                        reference.child(postid).setValue(hashMap);

                        progressDialog.dismiss();

                        Intent intent = new Intent(PostActivity.this, FindWorkActivity.class);

                        startActivity(intent);

                        finish();

                    }
                    else {
                        Toast.makeText(PostActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(PostActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            sImg.setImageURI(imageUri);
        }
        else{
            Toast.makeText(PostActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, MainActivity.class));
            finish();
        }
    }
}