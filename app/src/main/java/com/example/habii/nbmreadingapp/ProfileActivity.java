package com.example.habii.nbmreadingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseAuth mAuth;

    RadioButton radioButton;

    RadioGroup radioGroup;

    TextView username;

    CircleImageView profilepic;

    String userSide;

    Button submit;

    EditText id,phone,dpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        username = (TextView)findViewById(R.id.username);
        profilepic = (CircleImageView) findViewById(R.id.profilepic);

        id = (EditText)findViewById(R.id.idnumber);
        phone = (EditText)findViewById(R.id.phone);
        dpt = (EditText)findViewById(R.id.depertment);



        submit = (Button)findViewById(R.id.submit) ;

        if(mAuth.getCurrentUser() != null){

            username.setText(mAuth.getCurrentUser().getDisplayName());

            Glide.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(profilepic);

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                radioButton = (RadioButton)findViewById(i);

                userSide = radioButton.getText().toString();



            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!id.getText().toString().isEmpty()&&!phone.getText().toString().isEmpty()&&!dpt.getText().toString().isEmpty()){


                    if(userSide.equals("Teacher")){

                        Map<String,Object> post = new HashMap<>();
                        post.put("id",id.getText().toString());
                        post.put("phone", phone.getText().toString());
                        post.put("depertment", dpt.getText().toString());
                        post.put("initial", "MBS");
                        firebaseFirestore.collection("Teachers").document(mAuth.getCurrentUser().getUid()).set(post);



                    }else if(userSide.equals("Student")){

                        Map<String,Object> post = new HashMap<>();
                        post.put("id",id.getText().toString());
                        post.put("phone", phone.getText().toString());
                        post.put("depertment", dpt.getText().toString());
                        post.put("Semesterno", "8");
                        firebaseFirestore.collection("Students").document(mAuth.getCurrentUser().getUid()).set(post);

                    }else{


                        Toast.makeText(getApplicationContext(), "Select Your Acount Type", Toast.LENGTH_SHORT).show();

                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Fill Everything", Toast.LENGTH_SHORT).show();

                }


                setUpUser(mAuth.getCurrentUser().getUid());

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mAuth.signOut();
    }



    void setUpUser(String userid){


        final  String uid = userid;


        firebaseFirestore.collection("Teachers").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if(task.getResult().exists()){

                    finish();
                    startActivity(new Intent(ProfileActivity.this,MainActivity.class));

                }else{
                    
                    
                    firebaseFirestore.collection("Students").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                            if(task.getResult().exists()){

                                finish();
                                startActivity(new Intent(ProfileActivity.this,MainActivity.class));

                            }else{

                                Toast.makeText(ProfileActivity.this, "Failed To Submit", Toast.LENGTH_SHORT).show();
                                
                            }
                            
                            
                        }
                    });
                    
                }
            }
        });

    }
}
