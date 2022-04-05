package com.example.foodboxx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email, etPassword;
    Button btnLogin, btnForgetPassword, btnRegister;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        // auto login
//        if(auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        init();

        if (!checkConnection()){

            Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LoginActivity.this, "Connected to internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {

        email = findViewById(R.id.etemail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgetPassword = findViewById(R.id.btnForgetPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgetActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerActivity();
            }
        });

    }

    private void validation() {

        String emailString= email.getText().toString();
        String Password = etPassword.getText().toString();

        if (emailString.isEmpty()) {
            email.setError("Mobile number is required");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }
        if (Password.length() < 6) {
            etPassword.setError("Min password length should be 6 characters");
            etPassword.requestFocus();
            return;
        } else {
            auth.signInWithEmailAndPassword(emailString, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Intent main = new Intent (LoginActivity.this,DashboardActivity.class);
                        startActivity(main);
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            Intent main = new Intent (LoginActivity.this,MainActivity.class);
//            startActivity(main);
        }
    }

    private void forgetActivity(){
        Intent forget = new Intent(LoginActivity.this, SendOTPActivity.class);
        startActivity(forget);
    }

    private void registerActivity(){
        Intent register= new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(register);
    }

    private boolean checkConnection(){

        ConnectivityManager checkConnection =(ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);

        return checkConnection.getActiveNetworkInfo()!= null&&checkConnection.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}