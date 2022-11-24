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

public class Detail2 extends AppCompatActivity {
    Button next;
    EditText ecg,max_heart_rate,angina,oldpeak,slope,vessals,thal;
    private ProgressDialog loadingBar;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        next = findViewById(R.id.next_btn2);

        loadingBar = new ProgressDialog(this);

        ecg = findViewById(R.id.ecg);
        max_heart_rate = findViewById(R.id.Max_hr);
        angina = findViewById(R.id.Induced_angina);
        oldpeak = findViewById(R.id.oldpeak);
        slope = findViewById(R.id.slope);
        vessals = findViewById(R.id.Vessels);
        thal = findViewById(R.id.thal);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String ecg_st = ecg.getText().toString();
        String max_heart_rate_st = max_heart_rate.getText().toString();
        String angina_st = angina.getText().toString();
        String oldpeak_st = oldpeak.getText().toString();
        String slope_st = slope.getText().toString();
        String vessals_st = vessals.getText().toString();
        String thal_st = thal.getText().toString();

        if(ecg_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(max_heart_rate_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(angina_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(oldpeak_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(slope_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(vessals_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(thal_st.isEmpty()){
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.setTitle("Adding data");
            loadingBar.setMessage("please Wait......");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            addData(ecg_st,max_heart_rate_st,angina_st,oldpeak_st,slope_st,vessals_st,thal_st);
        }
    }

    private void addData(String one, String two, String three, String four, String five, String six, String seven) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("Patient").child(prevalent.CurrentOnlinePatient.getPhone()).exists())) {
                    HashMap<String, Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("ECG", one);
                    UserDataMap.put("MaxHeartRate", two);
                    UserDataMap.put("Angina",three);
                    UserDataMap.put("OldPeak", four);
                    UserDataMap.put("Slope", five);
                    UserDataMap.put("Vessals", six);
                    UserDataMap.put("Thal", seven);

                    RootRef.child("Patient").child(prevalent.CurrentOnlinePatient.getPhone()).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Data added successfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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