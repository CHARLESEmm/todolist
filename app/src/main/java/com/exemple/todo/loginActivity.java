package com.exemple.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class loginActivity extends AppCompatActivity {

    private EditText emaillogin,passwordLogin;

    private Button loginBtn;

    private TextView creatAccount;

    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaillogin = findViewById(R.id.email_login);
        passwordLogin = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.login_btn);
        creatAccount = findViewById(R.id.creatAccount);
        progressbar = findViewById(R.id.progress_bar);


        loginBtn.setOnClickListener((v)-> loginUser());
        creatAccount.setOnClickListener((v)-> startActivity(new Intent(loginActivity.this,MainActivity.class)));
    }

    void loginUser() {

        String email = emaillogin.getText().toString();
        String password = passwordLogin.getText().toString();


        boolean isvalid = validateData(email,password);
        if (!isvalid) {
            return;
        }

        LoginAccountInfirebase(email,password);
    }

    void LoginAccountInfirebase(String email, String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInprogress(false);
                if (task.isSuccessful())
                {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){

                        startActivity(new Intent(loginActivity.this,addNotes.class));

                            //c'est bon

                    }else{
                        Utility.showToast(loginActivity.this,"Verifiez votre mail ");
                    }
                }else {
                    //echec
                    Utility.showToast(loginActivity.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInprogress(boolean inprogress)
    {
        if (inprogress)
        {
            progressbar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else {
            progressbar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }


    boolean validateData(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emaillogin.setError("Email est invalide");
            return false;
        }
        if (password.length() < 7) {
            passwordLogin.setError("password invalid");
            return false;
        }
        return true;
    }

}