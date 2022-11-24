package com.hacktoroot.heartfly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hacktoroot.heartfly.prevalent.prevalent;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private CircleImageView profileImageView;
    private TextView fullNameEditText, Phone, emailText , age , gender;
    private ImageView profileChangeTextBtn;
    private ImageView logout;
    private Button rest;
    public TextView timerText;
    private Uri imageUri;
    private String myUrl = "";
    private StorageReference storageProfilePrictureRef;
    private static final int PICK_IMAGE = 1, RESULT_OK = -1;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Patient pictures");
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = (CircleImageView) view.findViewById(R.id.emp_profile_image);
        if(prevalent.CurrentOnlinePatient.getImage()!=null){
            Picasso.get().load(prevalent.CurrentOnlinePatient.getImage()).placeholder(R.drawable.ic_baseline_person_24).into(profileImageView);
        }

        fullNameEditText =  view.findViewById(R.id.emp_Name);
        fullNameEditText.setText(prevalent.CurrentOnlinePatient.getName());
        emailText = view.findViewById(R.id.emp_email_Value);
        emailText.setText(prevalent.CurrentOnlinePatient.getEmail());
        profileChangeTextBtn = view.findViewById(R.id.emp_profile_image);

        Phone = view.findViewById(R.id.emp_phone_value);
        Phone.setText(prevalent.CurrentOnlinePatient.getPhone());

        age = view.findViewById(R.id.age_number);
        gender = view.findViewById(R.id.gender_value);
        age.setText(prevalent.CurrentOnlinePatient.getAge());
        String gender_st = prevalent.CurrentOnlinePatient.getGender();
        if(gender_st.equals("1")){
            gender.setText("male");
        }else{
            gender.setText("female");
        }

//        logout = view.findViewById(R.id.logout_emp_text);
//        userInfoDetails(profileImageView);

//        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CropImage.activity(imageUri).
//                        setAspectRatio(1,1).
//                        start(Emp_settings_Activity.this);
//            }
//        });


        return view;

    }

//    private void userInfoDetails(CircleImageView profileImageView) {
//        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Patient").child(prevalent.CurrentOnlinePatient.getPhone());
//        adminref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    if(snapshot.child("Image").exists()){
//                        String image  = snapshot.child("Image").getValue().toString();
//                        Picasso.get().load(image).into(profileImageView);
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//
//    @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//            profileImageView.setImageURI(imageUri);
//            userInfosaved();
//
//        }else{
//            Toast.makeText(getContext(), "Error try again", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(Emp_settings_Activity.this,Emp_settings_Activity.class));
//            finish();
//
//        }
//    }
//
//    private void userInfosaved() {
//
//        if(checker.equals("clicked")){
//            uploadImage();
//        }
//    }
//
//    private void uploadImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("update profile");
//        progressDialog.setMessage("Please wait while updating the profile Image");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//        if(imageUri!=null){
//            final StorageReference fileref = storageProfilePrictureRef
//                    .child(prevalent.CurrentOnlineEmloyee.getIMEI() + ".jpg");
//            uploadTask = fileref.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                        @Override
//                        public Object then(@NonNull Task task) throws Exception {
//                            if(!task.isSuccessful()){
//                                throw task.getException();
//
//                            }
//
//                            return fileref.getDownloadUrl();
//                        }
//                    })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull  Task<Uri> task) {
//                            if(task.isSuccessful()){
//                                Uri downloaduri = task.getResult();
//                                myUrl  = downloaduri.toString();
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Employee");
//                                HashMap<String ,Object> hashMap = new HashMap<>();
//                                hashMap.put("Image",myUrl);
//                                ref.child(prevalent.CurrentOnlineEmloyee.getIMEI()).updateChildren(hashMap);
//                                progressDialog.dismiss();
//                                Toast.makeText(Emp_settings_Activity.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
//                            }else{
//                                progressDialog.dismiss();
//                                Toast.makeText(Emp_settings_Activity.this, "error while uploading please try again", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//        }else{
//            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//    private void userInfoDetails(CircleImageView profileImageView) {
//        DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.CurrentOnlineEmloyee.getIMEI());
//        adminref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    if(snapshot.child("Image").exists()){
//                        String image  = snapshot.child("Image").getValue().toString();
//
//
//                        Picasso.get().load(image).into(profileImageView);
//
//
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
}