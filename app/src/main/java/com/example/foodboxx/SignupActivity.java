package com.example.foodboxx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etname, etaddress, etemail, etphone, etpassword;
    private Button btnsignup;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        initt();
    }

    private void initt() {


        etname = findViewById(R.id.etname);
        etaddress = findViewById(R.id.etaddress);
        etemail = findViewById(R.id.etemail);
        etphone = findViewById(R.id.etphone);
        etpassword = findViewById(R.id.etpassword);
        btnsignup = findViewById(R.id.btnsign);
        progressBar = findViewById(R.id.progressBar);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validationn();
            }
        });
    }

    private void validationn() {

        String name = etname.getText().toString();
        String address = etaddress.getText().toString();
        String email = etemail.getText().toString().trim();
        String phone = etphone.getText().toString();
        String password = etpassword.getText().toString();

        if (name.isEmpty()) {
            etname.setError("Name is required");
            etname.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            etaddress.setError("Address is required");
            etaddress.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            etemail.setError("Email is required");
            etemail.requestFocus();
            return;
        }
//        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            etemail.setError("Please provide valid email");
//            etemail.requestFocus();
//            return;
//        }
        if(phone.isEmpty()){
            etphone.setError("Phone number is required");
            etphone.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etpassword.setError("Password is required");
            etpassword.requestFocus();
            return;
        }
        if(password.length()<6){
            etpassword.setError("Min password length should be 6 characters");
            etpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);



       mAuth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           User user=new User(name,email,address,phone,password);

                           FirebaseDatabase.getInstance().getReference("users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user)
                                   .addOnSuccessListener(sucess -> {
                               Toast.makeText(SignupActivity.this, "Success db", Toast.LENGTH_SHORT).show();
                           })
                                   .addOnFailureListener(fail -> {
                               Toast.makeText(SignupActivity.this, "failed db", Toast.LENGTH_SHORT).show();
                           });

                           Toast.makeText(SignupActivity.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);

                       }else{
            Toast.makeText(SignupActivity.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
                       }
                   }
               });


    }
}