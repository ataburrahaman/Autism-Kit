package com.example.atabur.autismkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LebelBordActivity extends AppCompatActivity {


    private ImageView prepage;
    private CardView lavel1;
    private CardView lavel2;
    private CardView lavel3;
    private CardView getResult1;
    private CardView getResult2;
    private CardView getResult3;
    private String Lavel1="label1";
    private String Lavel2="label2";
    private String Lavel3="label3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebel_bord);

        prepage=findViewById(R.id.prePage);
        lavel1 = findViewById(R.id.myLevel1);
        lavel2= findViewById(R.id.myLavel2);
        lavel3 = findViewById(R.id.myLavel3);
        getResult1= findViewById(R.id.myLevel1result);
        getResult2= findViewById(R.id.myLavel2result);
        getResult3 =findViewById(R.id.myLavel3result);

        getResult1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LebelBordActivity.this,WordLavel1Result.class);
                startActivity(i);
                finish();

            }
        });
        getResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LebelBordActivity.this,WordLavel2Result.class);
                startActivity(i);
                finish();
            }
        });
        getResult3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LebelBordActivity.this,WordLavel3Result.class);
                startActivity(i);
                finish();
            }
        });

        prepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LebelBordActivity.this,homeActivity.class);
                startActivity(i);
                finish();
            }
        });

        lavel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LebelBordActivity.this,QuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel1));
                startActivity(i);
            }
        });

        lavel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LebelBordActivity.this,QuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel2));
                startActivity(i);
            }
        });
        lavel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LebelBordActivity.this,QuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel3));
                startActivity(i);
            }
        });



    }

}
