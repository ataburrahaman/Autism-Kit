package com.example.atabur.autismkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView t1,t2,t3,t4;
    private Button preHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        t1=(TextView)findViewById(R.id.correctResult);
        t2 = (TextView)findViewById(R.id.incorrectResult);
        t3=(TextView)findViewById(R.id.totalResult);
        t4 =(TextView)findViewById(R.id.totWord);
        preHome = (Button)findViewById(R.id.previousHome);

        preHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,homeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Intent i = getIntent();

        String correct = i.getStringExtra("correct");
        String wrong = i.getStringExtra("incorrect");
        String question = i.getStringExtra("total");
        String wordcount = i.getStringExtra("wordcount");

        t1.setText(correct);
        t2.setText(wrong);
        t3.setText(question);
        t4.setText(wordcount);

    }
}
