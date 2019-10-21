package com.ostrue.app.authfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    private TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtName);

        Intent intent = getIntent();
        String mEmail = intent.getStringExtra("email");

        String mUser = (mEmail.contains("@")) ? mEmail.split("@")[0] : mEmail;
        txtName.setText("Username : " + mUser);
    }
}
