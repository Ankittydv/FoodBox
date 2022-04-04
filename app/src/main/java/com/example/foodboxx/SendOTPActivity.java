package com.example.foodboxx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otpactivity);

        final EditText inputMobile=findViewById(R.id.inputMobile);
        Button btnGetOTP = findViewById(R.id.btnGetOTP);

        final ProgressBar progressBar =findViewById(R.id.progressBar);

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputMobile.getText().toString().trim().isEmpty()){

                    Toast.makeText(SendOTPActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                btnGetOTP.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+ inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        SendOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                progressBar.setVisibility(View.GONE);
                                btnGetOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                progressBar.setVisibility(View.GONE);
                                btnGetOTP.setVisibility(View.VISIBLE);
                                Intent send =new Intent(getApplicationContext(),VerificationActivity.class);
                                send.putExtra("mobile", inputMobile.getText().toString());
                                send.putExtra("verificationId",verificationId);
                                startActivity(send);
                            }
                        }
                );



            }
        });

    }
}