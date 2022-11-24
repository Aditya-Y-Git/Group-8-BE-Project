package com.hacktoroot.heartfly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hacktoroot.heartfly.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        bottomNavigationView = findViewById(R.id.bottom_nav);
        loadfrag(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.home);




        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        loadfrag(fragment);
                        return;
//                case R.id.helpline:
//                    fragment = new Fragment2(); // add your fragment
//                    loadFragment(fragment);
//                    return true;

                    case R.id.home:
                        fragment = new HomeFragment(); // add your fragment
                        loadfrag(fragment);
                        return;

                    case R.id.doctors:
                    fragment = new DoctorsFragment(); // add your fragment
                    loadfrag(fragment);
                    return;

                }

            }
        });



    }

    public void loadfrag(Fragment fragment){
        FrameLayout frame = new FrameLayout(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }





}