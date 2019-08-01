package com.example.atabur.autismkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;


public class ImageLevelShow extends AppCompatActivity {

    private CardView lavel1;
    private CardView lavel2;
    private CardView lavel3;
    private CardView lavel1Result;
    private CardView lavel2Result;
    private CardView lavel3Result;
    private String Lavel1="label1";
    private String Lavel2="label2";
    private String Lavel3="label3";

    private ImageView previousPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_level_show);

        lavel1 = findViewById(R.id.myLevel1);
        lavel2= findViewById(R.id.myLavel2);
        lavel3 = findViewById(R.id.myLavel3);
        lavel1Result = findViewById(R.id.myLevel1result);
        lavel2Result =findViewById(R.id.myLavel2result);
        lavel3Result = findViewById(R.id.myLavel3result);

        previousPage = findViewById(R.id.prePage);

        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ImageLevelShow.this,homeActivity.class);
                i.putExtra("level",String.valueOf(Lavel1));
                startActivity(i);
            }
        });

        lavel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ImageLevelShow.this,imageQuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel1));
                startActivity(i);
            }
        });

        lavel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(ImageLevelShow.this,imageQuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel2));
                startActivity(i);
            }
        });
        lavel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(ImageLevelShow.this,imageQuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel3));
                startActivity(i);*/
            }
        });

        lavel3Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /* Intent i = new Intent(ImageLevelShow.this,imageQuizeActivity.class);
                i.putExtra("level",String.valueOf(Lavel3));
                startActivity(i);*/
            }
        });
        lavel2Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent i = new Intent(ImageLevelShow.this,ImageLavel2Result.class);
               // i.putExtra("level",String.valueOf(Lavel2));
                startActivity(i);
                finish();
            }
        });
        lavel1Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(ImageLevelShow.this,ImageLavel1Result.class);
              //  i.putExtra("level",String.valueOf(Lavel1));
                startActivity(i);
                finish();
            }
        });



    }
}
