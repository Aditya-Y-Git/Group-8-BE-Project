package com.hacktoroot.heartfly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hacktoroot.heartfly.models.Patient;
import com.hacktoroot.heartfly.prevalent.prevalent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Button btn;
    EditText phone,password;
    private String parentDBname = "Patient";
    private ProgressDialog loadingBar;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.pass);
        loadingBar = new ProgressDialog(this);


        dialog=new Dialog(LoginActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btn = findViewById(R.id.Login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_txt = phone.getText().toString();
                String pass_txt = password.getText().toString();
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("please Wait while checking Credentials..");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                AllowAccessToAccount(phone_txt,pass_txt);
            }
        });
    }

    public void AllowAccessToAccount(final String Phone,final String Password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBname).child(Phone).exists()){
                    Patient patient = dataSnapshot.child(parentDBname).child(Phone).getValue(Patient.class);
                    if (patient.getPhone().equals(Phone)){
                        if(patient.getPassword().equals(Password)){
                            if(parentDBname.equals("Patient")){
                                Toast.makeText(getApplicationContext(), "Welcome User You are Logged In Successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),Details.class);
                                intent.putExtra("Phone",Phone);
                                prevalent.CurrentOnlinePatient = patient;
                                startActivity(intent);
                            }
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Incorrect Password..", Toast.LENGTH_SHORT).show();

                        }
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials..PLease Try again with another Phone Number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    dialog.show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private boolean validatePass(String password_str) {
        String[] regex = {".*\\d+.*"
                , ".*[a-z].*", ".*[A-Z].*"
                , ".*[@#$%^&+=].*"
                , ".*/\\s/.*", ".{8,20}.*"};
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regex[0]);
        matcher = pattern.matcher(password_str);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one digit");
            return false;
        }
        pattern = Pattern.compile(regex[1]);
        matcher = pattern.matcher(password_str);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one lower case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[2]);
        matcher = pattern.matcher(password_str);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one upper case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[3]);
        matcher = pattern.matcher(password_str);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one special character");
            return false;
        }
        pattern = Pattern.compile(regex[4]);
        matcher = pattern.matcher(password_str);
        if (matcher.matches()) {
            password.setError("White spaces are not allowed");
            return false;
        }
        pattern = Pattern.compile(regex[5]);
        matcher = pattern.matcher(password_str);
        if (!matcher.matches()) {
            password.setError("Password should contain 8 to 20 characters only");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String Email) {

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);
        if (!matcher.matches()) {
            phone.setError("Please Provide a valid E-mail");
            return false;
        }
        return true;
    }





}