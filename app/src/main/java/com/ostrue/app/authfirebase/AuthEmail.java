package com.ostrue.app.authfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthEmail extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnMasuk;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_email);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText)findViewById(R.id.tv_email);
        edtPass = (EditText)findViewById(R.id.tv_pass);
        btnMasuk = (Button) findViewById(R.id.btn_masuk);
        btnDaftar = (Button)findViewById(R.id.btn_daftar);

        btnMasuk.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_masuk     : signIn(); break;
            case R.id.btn_daftar    : signUp(); break;
        }
    }

    private void signIn() {
        Log.d("LOGIN Activity", "Sign In");
        if (!validateForm()) {
            return;
        }

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Sign In", "OnComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(AuthEmail.this, "Welcome " + user.getEmail() + " :)", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AuthEmail.this, Profile.class);
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(AuthEmail.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AuthEmail.this,"Sign In Failed! " + e.getMessage(),Toast.LENGTH_SHORT);
                        Log.e("Sign In Failed",e.getMessage());
                    }
                });
    }

    private void signUp() {
        Log.d("Login Activity", "Sign Up");
        if (!validateForm()) {
            return;
        }

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Sign Up", "OnComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            String username = (user.getEmail().contains("@")) ? user.getEmail().split("@")[0] : user.getEmail();

                            // write to realtime database
                            Peserta peserta = new Peserta(username, user.getEmail());
                            mDatabase.child("peserta").child(user.getUid()).setValue(peserta);

                            startActivity(new Intent(AuthEmail.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(AuthEmail.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AuthEmail.this,"Error :" + e.getMessage(),Toast.LENGTH_SHORT);
                        Log.e("Error :", e.getMessage());
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Required");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Required");
            result = false;
        } else {
            edtPass.setError(null);
        }

        return result;
    }

}
