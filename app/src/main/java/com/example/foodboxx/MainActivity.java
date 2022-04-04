package com.example.foodboxx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnuser,btnvendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void init(){

        btnuser=findViewById(R.id.btnuser);
        btnvendor=findViewById(R.id.btnvendor);

        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userloginpage();

            }
        });

        
    }

    private void userloginpage(){
        Intent u= new Intent(MainActivity.this,LoginActivity.class);
        startActivity(u);
    }




}
