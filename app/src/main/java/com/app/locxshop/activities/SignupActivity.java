package com.app.locxshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.locxshop.activities.MainActivity;
import com.app.locxshop.R;
//import com.app.locxshop.others.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {


    EditText name, email, password;
    Button btnSignup, btnlog;
    ImageView imageView;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        imageView = findViewById(R.id.iv_logo);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignup = findViewById(R.id.btn_sign_up);
        btnlog = findViewById(R.id.sig_btn);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd = new ProgressDialog(SignupActivity.this);
                pd.setMessage("Please Wait........");
                pd.show();

                String str_name = name.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if(TextUtils.isEmpty((str_name)) || TextUtils.isEmpty((str_email)) || TextUtils.isEmpty((str_password))){
                    Toast.makeText(SignupActivity.this, "All Field is Req", Toast.LENGTH_SHORT).show();

                }
                else if(str_password.length() < 6){
                    Toast.makeText(SignupActivity.this, "Password must be 6 words", Toast.LENGTH_SHORT).show();

                }
                else{
                    btnSignup(str_name, str_email, str_password );
                }

            }
        });


    }
    private void btnSignup(final String name, final String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseuser = auth.getCurrentUser();
                            String userid = firebaseuser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("userid", userid);
                            hashMap.put("name", name.toLowerCase());
                            hashMap.put("email", email);
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/localshop-c6b0f.appspot.com/o/pp.png?alt=media&token=bcc2801e-29bd-4d08-9f9a-7c6e2d3754e3");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(SignupActivity.this, "you cant" + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
}