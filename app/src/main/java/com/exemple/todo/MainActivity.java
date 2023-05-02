package com.exemple.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email_edit_text, passwordEdit, confirm_password;
    private Button accountBtn;
    private ProgressBar progress_bar;
    private TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_edit_text = findViewById(R.id.email_edit_text);
        passwordEdit = findViewById(R.id.password_edit);
        confirm_password = findViewById(R.id.confirm_password);
        accountBtn = findViewById(R.id.account_btn);
        loginBtn = findViewById(R.id.login_btn);
        progress_bar = findViewById(R.id.progress_bar);

        accountBtn.setOnClickListener(v -> createAccount());
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
        });
    }

    void createAccount() {
       String email = email_edit_text.getText().toString();
       String password = passwordEdit.getText().toString();
       String confirmPassword = confirm_password.getText().toString();

       boolean isvalid = validateData(email,password,confirmPassword);
       if (!isvalid){
           return;
       }

       creaAccountInFirebase(email,password);
    }

    void creaAccountInFirebase(String email, String password) {

        changeInprogress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this,

                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInprogress(false);
                        if (task.isSuccessful())
                        {
                            Utility.showToast(MainActivity.this,"IT GOOD");
                            Toast.makeText(MainActivity.this, "IT GOOD", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }else {
                            //echec
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );

    }

    void changeInprogress(boolean inprogress)
    {
        if (inprogress)
        {
            progress_bar.setVisibility(View.VISIBLE);
            accountBtn.setVisibility(View.GONE);
        }else {
            progress_bar.setVisibility(View.GONE);
            accountBtn.setVisibility(View.VISIBLE);
        }
    }


    boolean validateData(String email, String password, String confirmPassword) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_edit_text.setError("Email est invalide");
            return false;
        }
        if (password.length() < 7) {
            passwordEdit.setError("password invalid");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirm_password.setError("password pas identique");
            return false;
        }
        return true;
    }


}