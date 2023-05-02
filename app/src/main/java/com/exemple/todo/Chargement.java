package com.exemple.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Chargement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);

        // Définir un délai de 4 secondes avant le lancement de l'activité MainActivity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null){
                    startActivity(new Intent(Chargement.this, loginActivity.class));
                } else {
                    startActivity(new Intent(Chargement.this, MainActivity.class));
                }
                finish();
            }
        }, 4000);
    }}
