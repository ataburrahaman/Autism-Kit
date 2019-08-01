package com.example.atabur.autismkit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class welcomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Shared shared=new Shared(welcomeActivity.this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
             if(shared.getName()!=""&&shared.getPassword()!=""){

                    Intent homeIntent = new Intent(welcomeActivity.this, homeActivity.class);
                    homeIntent.putExtra("name",shared.getName());
                    startActivity(homeIntent);
                    finish();
                }
                else {
                    Intent homeIntent = new Intent(welcomeActivity.this, LoginActivity.class);
                    startActivity(homeIntent);
                    finish();

                }
            }
        },SPLASH_TIME_OUT);
    }
}
