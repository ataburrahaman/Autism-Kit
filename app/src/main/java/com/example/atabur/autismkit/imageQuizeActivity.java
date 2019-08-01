package com.example.atabur.autismkit;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class imageQuizeActivity extends AppCompatActivity {

    private ImageButton option1;
    private ImageButton option2;
    private ImageButton option3;
    private ImageButton option4;
    private TextView question;
    private TextView color;
    long elapsedMillis;
    private Button backhome,sumbitresult;
    DatabaseReference databaseImagequize;

    Chronometer simpleChronometer;
    int total=0;
    int correct = 0;
    int wrong =0;
    int totaltry=0;
    int totalTime;

    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_quize);

        Intent i = getIntent();
        String level = i.getStringExtra("level");
        databaseImagequize =  FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ImageResult").child(level);

        option1=(ImageButton) findViewById(R.id.option1);
        option2=(ImageButton) findViewById(R.id.option2);
        option3=(ImageButton) findViewById(R.id.option3);
        option4 =(ImageButton) findViewById(R.id.option4);

        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer
        simpleChronometer.start();

        question =(TextView) findViewById(R.id.question);
        color = (TextView) findViewById(R.id.color);
        backhome = (Button)findViewById(R.id.backbtn);
        sumbitresult = (Button)findViewById(R.id.submitbtn);
        sumbitresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total=total-1;
                totaltry =correct+wrong;
                /* Date timeview = Calendar.getInstance().getTime();
            String timestm= timeview.toString(); */
                String timestm = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault()).format(new Date());

                Calendar calendar =Calendar.getInstance();
                        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                String timeliv= format.format(calendar.getTime());

                elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
                elapsedMillis=elapsedMillis/1000;
                totalTime=(int)elapsedMillis;
                String res=predictionCalculation(correct,wrong,totalTime);

                resultStore resStore=new resultStore(correct,wrong,totaltry,total,totalTime,timestm,timeliv,res);

                String id = databaseImagequize.push().getKey();

                /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/


                databaseImagequize.child(id).setValue(resStore).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){


                            Intent i =new Intent(imageQuizeActivity.this,ResultActivity.class);
                            i.putExtra("correct",String.valueOf(correct));
                            i.putExtra("incorrect",String.valueOf(wrong));
                            i.putExtra("total",String.valueOf(totaltry));
                            i.putExtra("wordcount",String.valueOf(total));
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            finish();

                        }
                        else{
                            // progressDialog.dismiss();
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"You are already Register",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(imageQuizeActivity.this, imageQuizeActivity.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), " Please Try again....", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(imageQuizeActivity.this, imageQuizeActivity.class));
                            }
                        }
                    }
                });

                /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/











               /*

                Intent i =new Intent(imageQuizeActivity.this,ResultActivity.class);

                i.putExtra("correct",String.valueOf(correct));
                i.putExtra("incorrect",String.valueOf(wrong));
                i.putExtra("total",String.valueOf(totaltry));
                i.putExtra("wordcount",String.valueOf(total));
                startActivity(i);

                finish();
                */

            }
        });

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(imageQuizeActivity.this,ImageLevelShow.class);
                startActivity(intent);
                finish();
            }
        });



        updateQuestion();
    }

    private void updateQuestion() {
        total++;

        if (total>10){
            total=total-1;
            totaltry =correct+wrong;
           /* Date timeview = Calendar.getInstance().getTime();
            String timestm= timeview.toString(); */
           String timestm = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault()).format(new Date());
            Calendar calendar =Calendar.getInstance();
            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
            String timeliv= format.format(calendar.getTime());
            elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
            elapsedMillis=elapsedMillis/1000;
            totalTime=(int)elapsedMillis;

            String res=predictionCalculation(correct,wrong,totalTime);

            resultStore resStore=new resultStore(correct,wrong,totaltry,total,totalTime,timestm,timeliv,res);

            /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

                String id = databaseImagequize.push().getKey();
            databaseImagequize.child(id).setValue(resStore).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){


                        Intent i =new Intent(imageQuizeActivity.this,ResultActivity.class);
                        i.putExtra("correct",String.valueOf(correct));
                        i.putExtra("incorrect",String.valueOf(wrong));
                        i.putExtra("total",String.valueOf(totaltry));
                        i.putExtra("wordcount",String.valueOf(total));
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        finish();

                    }
                    else{
                        // progressDialog.dismiss();
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(getApplicationContext(),"You are already Register",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(imageQuizeActivity.this, imageQuizeActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), " Please Try again....", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(imageQuizeActivity.this, imageQuizeActivity.class));
                        }
                    }
                }
            });

            /*
            //Open result activity.
            Intent i =new Intent(imageQuizeActivity.this,ResultActivity.class);
            total=total-1;
            totaltry =correct+wrong;
            i.putExtra("correct",String.valueOf(correct));
            i.putExtra("incorrect",String.valueOf(wrong));
            i.putExtra("total",String.valueOf(totaltry));
            i.putExtra("wordcount",String.valueOf(total));
            startActivity(i);

            finish();
            */

        }
        else{

            elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
            elapsedMillis=elapsedMillis/1000;
            totalTime=(int)elapsedMillis;
           // Toast.makeText(getApplicationContext(),"Time Stamp: "+elapsedMillis,Toast.LENGTH_SHORT).show();
            Intent i = getIntent();
            String level = i.getStringExtra("level");
            reference = FirebaseDatabase.getInstance().getReference().child("Object").child(level).child(String.valueOf(total));

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final Question questioncls = dataSnapshot.getValue(Question.class);

                    question.setText(questioncls.getQuestion());
                  /*  option1.setText(questioncls.getOption1());
                    option2.setText(questioncls.getOption2());
                    option3.setText(questioncls.getOption3());
                    option4.setText(questioncls.getOption4());*/


                    Picasso.get().load(questioncls.getOption1()).into(option1);
                    Picasso.get().load(questioncls.getOption2()).into(option2);
                    Picasso.get().load(questioncls.getOption3()).into(option3);
                    Picasso.get().load(questioncls.getOption4()).into(option4);




                    option1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(questioncls.getOption1().equals(questioncls.getAnswar())){
                                Handler handler = new Handler();
                                color.setBackgroundColor(Color.GREEN);
                                option1.setBackgroundColor(Color.GREEN);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        color.setBackgroundColor(Color.WHITE);
                                        option1.setBackgroundColor(Color.WHITE);
                                        updateQuestion();
                                    }
                                },1000);
                            }else{

                                //answar is wrong we will find the correct question
                                wrong++;
                                color.setBackgroundColor(Color.RED);
                                option1.setBackgroundColor(Color.RED);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        color.setBackgroundColor(Color.WHITE);
                                        option1.setBackgroundColor(Color.WHITE);

                                        updateQuestion();
                                    }
                                },1500);
                            }


                        }
                    });

                    option2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(questioncls.getOption2().equals(questioncls.getAnswar())){
                                Handler handler = new Handler();
                                color.setBackgroundColor(Color.GREEN);
                                option2.setBackgroundColor(Color.GREEN);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        color.setBackgroundColor(Color.WHITE);
                                        option2.setBackgroundColor(Color.WHITE);

                                        updateQuestion();
                                    }
                                },1000);
                            }else{

                                //answar is wrong we will find the correct question
                                wrong++;
                                color.setBackgroundColor(Color.RED);
                                option2.setBackgroundColor(Color.RED);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        color.setBackgroundColor(Color.WHITE);
                                        option2.setBackgroundColor(Color.WHITE);
                                        updateQuestion();
                                    }
                                },1000);
                            }


                        }



                    });
                    option3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(questioncls.getOption3().toString().equals(questioncls.getAnswar())){
                                Handler handler = new Handler();
                                color.setBackgroundColor(Color.GREEN);
                                option3.setBackgroundColor(Color.GREEN);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        color.setBackgroundColor(Color.WHITE);
                                        option3.setBackgroundColor(Color.WHITE);

                                        updateQuestion();
                                    }
                                },1000);
                            }else{

                                //answar is wrong we will find the correct question
                                wrong++;
                                color.setBackgroundColor(Color.RED);
                                option3.setBackgroundColor(Color.RED);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        color.setBackgroundColor(Color.WHITE);
                                        option3.setBackgroundColor(Color.WHITE);

                                        updateQuestion();
                                    }
                                },1000);
                            }



                        }
                    });

                    option4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(questioncls.getOption4().equals(questioncls.getAnswar())){
                                Handler handler = new Handler();
                                color.setBackgroundColor(Color.GREEN);
                                option4.setBackgroundColor(Color.GREEN);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        color.setBackgroundColor(Color.WHITE);
                                        option4.setBackgroundColor(Color.WHITE);

                                        updateQuestion();
                                    }
                                },1000);
                            }else{

                                //answar is wrong we will find the correct question
                                wrong++;
                                color.setBackgroundColor(Color.RED);
                                option4.setBackgroundColor(Color.RED);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        color.setBackgroundColor(Color.WHITE);
                                        option4.setBackgroundColor(Color.WHITE);

                                        updateQuestion();
                                    }
                                },1000);
                            }



                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private String predictionCalculation(int correct, int wrong, int totalTime) {
        String val;
        double y=(double)correct*.8-wrong*.3-.1*(totalTime/10);
        if(y>5){
            val= "Good";
        }else if (y>2.5 && y<=5){
            val ="Medium";
        }else {
            val ="Not Development";
        }
        return val;
    }
}
