package com.hacktoroot.heartfly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_activity extends AppCompatActivity {
    Button register;
    EditText name;
    EditText phone;
    EditText email;
    EditText password;
    private ProgressDialog loadingBar;
    TextView logintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        register = findViewById(R.id.register);
        name = findViewById(R.id.edit_text1);
        phone = findViewById(R.id.edit_text);
        email = findViewById(R.id.edit_text2);
        password = findViewById(R.id.edit_text3);
        logintext= findViewById(R.id.login_text);

        loadingBar = new ProgressDialog(this);

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createaccount();
//
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrLoginEmail = email.getText().toString();
                String StrLoginPassword = password.getText().toString();
                if(validateEmail(StrLoginEmail) && validatePass(StrLoginPassword)){
                   createaccount();
                }else{
                    Toast.makeText(Register_activity.this, "PLease try again ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createaccount() {
        String Name = name.getText().toString();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if (Name.isEmpty()) {
            Toast.makeText(this, "Email feild cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (Phone.isEmpty()) {
            Toast.makeText(this, "Phone feild cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (Email.isEmpty()) {
            Toast.makeText(this, "Email feild canot be empty", Toast.LENGTH_SHORT).show();
        } else if (Password.isEmpty()) {
            Toast.makeText(this, "Password feild ", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("please wait while checking Credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Validate(Name, Phone, Email, Password);
        }
    }

    private void Validate(final String name, final String phone, final String email, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Patient").child(phone).exists())) {
                    HashMap<String, Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("Email", email);
                    UserDataMap.put("Name", name);
                    UserDataMap.put("Phone", phone);
                    UserDataMap.put("Password", password);


                    RootRef.child("Patient").child(phone).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Congratulations..Your Account Has been Created Sucessfully.. ", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(Register_activity.this, LoginActivity.class);
                                        startActivity(intent);

                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Somthing Went Wrong.. Please Try Again After Some time..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                } else {
                    Toast.makeText(getApplicationContext(), "This " + email + " id already Exists..", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Please Try Again Using Another email..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private boolean validatePass(String Password) {

        String[] regex = {".*\\d+.*"
                , ".*[a-z].*", ".*[A-Z].*"
                , ".*[@#$%^&+=].*"
                , ".*/\\s/.*", ".{8,20}.*"};
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regex[0]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one digit");
            return false;
        }
        pattern  = Pattern.compile(regex[1]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one lower case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[2]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one upper case alphabet");
            return false;
        }
        pattern = Pattern.compile(regex[3]);
        matcher = pattern.matcher(Password);
        if (!matcher.matches()) {
            password.setError("Password should contain at least one special character");
            return false;
        }
        pattern = Pattern.compile(regex[4]);
        matcher = pattern.matcher(Password);
        if (matcher.matches()) {
            password.setError("White spaces are not allowed");
            return false;
        }
        pattern = Pattern.compile(regex[5]);
        matcher = pattern.matcher(Password);
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
            email.setError("Please Provide a valid E-mail");
            return false;
        }
        return true;
    }
}