package com.example.socialucas.activities;
import com.example.socialucas.R;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialucas.adapters.ProfileAdapter;
import com.example.socialucas.components.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ChatHomeActivity extends AppCompatActivity {
    RecyclerView recyclerViewProfiles;
    List<User> profiles, allProfiles;
    ProfileAdapter profileAdapter;
    ImageView closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);
        recyclerViewProfiles = findViewById(R.id.recyclerview_profiles);
        closeButton = findViewById(R.id.btn_close);

        profiles = new ArrayList<>();
        allProfiles = new ArrayList<>();
        profileAdapter = new ProfileAdapter(this, profiles, "MESSAGE");
        recyclerViewProfiles.setHasFixedSize(true);
        recyclerViewProfiles.setAdapter(profileAdapter);

        readProfiles();
        closeButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void readProfiles() {
        CollectionReference userReference = FirebaseFirestore.getInstance().collection("Users");
        userReference.get().addOnSuccessListener(usersSnapshots -> {
            profiles.clear();
            for (DocumentSnapshot userSnapshot : usersSnapshots) {
                User user = userSnapshot.toObject(User.class);
                if (user.getId().equals(FirebaseAuth.getInstance().getUid())) continue;
                profiles.add(user);
            }
            profileAdapter.filterList(profiles);
        });
    }
}