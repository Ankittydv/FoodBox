package com.example.foodboxx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText etMobilenumber;
    Button btnSendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        inittt();

    }

    private void inittt() {

        etMobilenumber = findViewById(R.id.etMobilenumber);
        btnSendOTP = findViewById(R.id.btnSendOTP);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validationnn();

            }
        });
    }

    private void validationnn() {

        String Mobilenumber = etMobilenumber.getText().toString();

        if (Mobilenumber.isEmpty()) {
            etMobilenumber.setError("Mobile number is required");
            etMobilenumber.requestFocus();
            return;

        } else {
            
            Toast.makeText(ForgetPasswordActivity.this, "Send OTP", Toast.LENGTH_SHORT).show();

        }
    }
}