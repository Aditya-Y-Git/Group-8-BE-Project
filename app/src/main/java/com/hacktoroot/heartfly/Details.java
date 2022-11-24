package com.hacktoroot.heartfly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hacktoroot.heartfly.prevalent.prevalent;

import java.util.HashMap;

public class Details extends AppCompatActivity {
    Button next;
    EditText age,gender,resting_blood_pressure,Serum_Cholesterol,Fasting_Blood_sugar,chest_type;
    private ProgressDialog loadingBar;
    private Dialog dialog;
//    private errorHeading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        next = findViewById(R.id.next_btn);
        age = findViewById(R.id.age_P);
        gender = findViewById(R.id.gender_P);
        chest_type = findViewById(R.id.cheastType);
        resting_blood_pressure = findViewById(R.id.resting_blood);
        Serum_Cholesterol = findViewById(R.id.ch);
        Fasting_Blood_sugar = findViewById(R.id.fbs);
//        maximum_heart_rate = findViewById(R.id.Thalach);
        loadingBar = new ProgressDialog(this);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();

            }
        });
    }

    private void createAccount() {
        String one = age.getText().toString();
        String two = gender.getText().toString();
        String three = chest_type.getText().toString();
        String four = resting_blood_pressure.getText().toString();
        String five = Serum_Cholesterol.getText().toString();
        String six = Fasting_Blood_sugar.getText().toString();

        if(one.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(two.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(three.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(four.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(five.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(six.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.setTitle("Adding data");
            loadingBar.setMessage("please Wait......");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Adddata(one,two,three,four,five,six);
            prevalent.CurrentOnlinePatient.setAge(one);
            prevalent.CurrentOnlinePatient.setGender(two);
            prevalent.CurrentOnlinePatient.setChestType(three);
            prevalent.CurrentOnlinePatient.setRestingBloodPressure(four);
            prevalent.CurrentOnlinePatient.setSerumCholestrol(five);
            prevalent.CurrentOnlinePatient.setFastingBloodSugar(six);
        }

    }

    private void Adddata(String one, String two,String three, String four, String five, String six) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("Patient").child(prevalent.CurrentOnlinePatient.getPhone()).exists())) {
                    HashMap<String, Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Age", one);
                    UserDataMap.put("Gender", two);
                    UserDataMap.put("ChestType",three);
                    UserDataMap.put("RestingBloodPressure", four);
                    UserDataMap.put("SerumCholestrol", five);
                    UserDataMap.put("FastingBloodSugar", six);
//                    UserDataMap.put("Maximum_heart_rate_achieved", six);


                    RootRef.child("Patient").child(prevalent.CurrentOnlinePatient.getPhone()).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Data added successfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), Detail2.class);
                                        startActivity(intent);

                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong pleases try again after some time", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}