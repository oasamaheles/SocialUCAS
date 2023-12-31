package com.example.socialucas.activities;
import com.example.socialucas.R;
import com.example.socialucas.fragments.HomeFragment;
import com.example.socialucas.fragments.ProfileFragment;
import com.example.socialucas.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseUser user;
    String currentPage;

    @Override
    public void onBackPressed() {
        if (currentPage.equals("HOME")) {
            super.onBackPressed();
        } else {
            currentPage = "HOME";
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        user = FirebaseAuth.getInstance().getCurrentUser();

        String page = getIntent().getStringExtra("page");
        if (page != null) {
            if (page.equals("PROFILE")) {
                currentPage = "PROFILE";
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
            }
        } else {
            currentPage = "HOME";
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    currentPage = "HOME";
                    break;
                case R.id.nav_search:
                    currentPage = "SEARCH";
                    break;
                case R.id.nav_profile:
                    currentPage = "PROFILE";
                    break;
            }
            startFragment(currentPage);
            return true;
        });
        startFragment(currentPage);
    }

    public void startFragment(String fragment) {
        Fragment frag = null;
        switch (fragment) {
            case "PROFILE":
                frag = new ProfileFragment();
                currentPage = "PROFILE";
                break;
            case "SEARCH":
                frag = new SearchFragment();
                currentPage = "SEARCH";
                break;
            case "HOME":
                frag = new HomeFragment();
                currentPage = "HOME";
                break;
        }
        if (frag != null) startFragment(frag);
    }
}