package com.example.atabur.autismkit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class QuizeActivity extends AppCompatActivity {

    private TextView question;
    SpeechRecognizer mspeechRecognizer;
    Intent mspeechRecognizerIntent;
    private TextToSpeech myTTS;
    DatabaseReference reference;
    int total=1;
    int correct = 0;
    int wrong =0;
    int totaltry=0;
    String name;
    String recgSound;
    private Button soundBotton;
     private Button prvouspage,nextText,previoustext,submitResult;
    DatabaseReference databaseWordquize;
    Chronometer simpleChronometer;
    long elapsedMillis;
    int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize);


        checkPermission();

        Intent i = getIntent();
        String level = i.getStringExtra("level");
        mspeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mspeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mspeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        databaseWordquize =  FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("WordResult").child(level);

        soundBotton = (Button)findViewById(R.id.texttoSound);
        nextText = (Button)findViewById(R.id.nextView);
        previoustext = (Button)findViewById(R.id.previousView);

        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer
        simpleChronometer.start();

        question=(TextView)findViewById(R.id.textQuestion);
        mspeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        mspeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches!= null){
                    //editText.setText(matches.get(0));
                    recgSound = matches.get(0);

                    Toast.makeText(QuizeActivity.this, "You Told:  "+recgSound , Toast.LENGTH_SHORT).show();

                    recgSound=recgSound.toLowerCase();
                    //name=name.toLowerCase();
                    if(recgSound.equals(name)){
                        total++;
                        correct++;
                        updateQuestion(total);

                    }
                    else{
                        mspeechRecognizer.startListening(mspeechRecognizerIntent);
                        Toast.makeText(QuizeActivity.this, "Please Try Again Can't Match your language You Told:  "+recgSound , Toast.LENGTH_SHORT).show();
                        wrong++;
                        updateQuestion(total);
                    }
                }
              //  precessResult(matches.get(0));



            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
       /*
        reference = FirebaseDatabase.getInstance().getReference().child("Question").child("label1").child(String.valueOf(total));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.getValue(String.class);

               question.setText(name);
                Toast.makeText(QuizeActivity.this, "Value : " + name, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
        elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
        elapsedMillis=elapsedMillis/1000;
        totalTime=(int)elapsedMillis;

        initializeTextToSpeech();
        updateQuestion(total);

        submitResult =(Button)findViewById(R.id.subResult);

        submitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mspeechRecognizer.stopListening();

                total=total-1;
                totaltry =correct+wrong;
                elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
                elapsedMillis=elapsedMillis/1000;
                totalTime=(int)elapsedMillis;
                /* Date timeview = Calendar.getInstance().getTime();
            String timestm= timeview.toString(); */
                String timestm = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault()).format(new Date());

                Calendar calendar =Calendar.getInstance();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                String timeliv= format.format(calendar.getTime());

                String res=predictionCalculation(correct,wrong,totalTime);

                resultStore resStore=new resultStore(correct,wrong,totaltry,total,totalTime,timestm,timeliv,res);

                String id = databaseWordquize.push().getKey();
                databaseWordquize.child(id).setValue(resStore).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i =new Intent(QuizeActivity.this,ResultActivity.class);

                        i.putExtra("correct",String.valueOf(correct));
                        i.putExtra("incorrect",String.valueOf(wrong));
                        i.putExtra("total",String.valueOf(totaltry));
                        i.putExtra("wordcount",String.valueOf(total));
                        startActivity(i);
                        finish();

                    }
                });


            }

            private String predictionCalculation(int correct, int wrong, int totalTime) {
                String val;
                double y=(double)correct*.8-wrong*.2-.1*(totalTime/10);
                if(y>4){
                    val= "Good";
                }else if (y>2 && y<=4){
                    val ="Medium";
                }else {
                    val ="Not Development";
                }
                return val;
            }
        });

        prvouspage = (Button)findViewById(R.id.priviousPage);

        previoustext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total>0) {
                    total--;
                    updateQuestion(total);
                }
                else{
                    Toast.makeText(QuizeActivity.this, "Previous No Words Avalable ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                updateQuestion(total);
            }
        });
     prvouspage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent =new Intent(QuizeActivity.this,LebelBordActivity.class);
             startActivity(intent);
             mspeechRecognizer.stopListening();
             finish();
         }
     });


        soundBotton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // mspeechRecognizer.stopListening();
                        // Toast.makeText(QuizeActivity.this, "Listening..... " , Toast.LENGTH_SHORT).show();
                        //  mspeechRecognizer.startListening(mspeechRecognizerIntent);
                        updateQuestion(total);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        //editText.setText("");
                        //editText.setHint("Listening...");
                        //mspeechRecognizer.startListening(mspeechRecognizerIntent);
                        //  mspeechRecognizer.stopListening();

                        speak(name);
                        updateQuestion(total);

                        break;

                }
                return false;

            }
        });

    }
    private void updateQuestion(int tot) {
        elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
        elapsedMillis=elapsedMillis/1000;
        totalTime=(int)elapsedMillis;

        if (tot > 26) {

            //Open result activity.

            mspeechRecognizer.stopListening();
            total=total-1;
            totaltry =correct+wrong;
                /* Date timeview = Calendar.getInstance().getTime();
            String timestm= timeview.toString(); */
            String timestm = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault()).format(new Date());

            Calendar calendar =Calendar.getInstance();
            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
            String timeliv= format.format(calendar.getTime());

            String res=predictionCalculation(correct,wrong,totalTime);

            resultStore resStore=new resultStore(correct,wrong,totaltry,total,totalTime,timestm,timeliv,res);

            String id = databaseWordquize.push().getKey();
            databaseWordquize.child(id).setValue(resStore).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent i =new Intent(QuizeActivity.this,ResultActivity.class);

                    i.putExtra("correct",String.valueOf(correct));
                    i.putExtra("incorrect",String.valueOf(wrong));
                    i.putExtra("total",String.valueOf(totaltry));
                    i.putExtra("wordcount",String.valueOf(total));
                    startActivity(i);
                    finish();

                }
            });

        } else {

            elapsedMillis =  SystemClock.elapsedRealtime() -  simpleChronometer.getBase();
            elapsedMillis=elapsedMillis/1000;
            totalTime=(int)elapsedMillis;
           // Toast.makeText(getApplicationContext(),"Time Stamp: "+elapsedMillis,Toast.LENGTH_SHORT).show();
            Intent i = getIntent();
            String level = i.getStringExtra("level");
            reference = FirebaseDatabase.getInstance().getReference().child("Question").child(level).child(String.valueOf(tot));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    name = dataSnapshot.getValue(String.class);
                    name= name.toLowerCase();

                    question.setText(dataSnapshot.getValue(String.class));
                    mspeechRecognizer.startListening(mspeechRecognizerIntent);
                  //  Toast.makeText(QuizeActivity.this, "Listening..... "+name , Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }

    private String predictionCalculation(int correct, int wrong, int totalTime) {
        String val;
        double y=(double)correct*.8-wrong*.2-.1*(totalTime/10);
        if(y>4){
            val= "Good";
        }else if (y>2 && y<=4){
            val ="Medium";
        }else {
            val ="Not Development";
        }
        return val;
    }

    private void precessResult(String command) {
        //Here Check Our Result User Voice What the user say;
        Toast.makeText(QuizeActivity.this, "Question : " + name, Toast.LENGTH_SHORT).show();
        Toast.makeText(QuizeActivity.this, "Value : " + command, Toast.LENGTH_SHORT).show();

    }

    private void initializeTextToSpeech() {
        mspeechRecognizer.stopListening();
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (myTTS.getEngines().size()==0){
                    Toast.makeText(QuizeActivity.this,"There is no TTS engine on your device",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    myTTS.setLanguage(Locale.US);
                    speak("Hello ! I am ready ");
                }
            }
        });
    }

    private void speak(String message) {
        mspeechRecognizer.stopListening();
        if (Build.VERSION.SDK_INT >=21){
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }
    }


    private void checkPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

            if (!(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)
                    ==PackageManager.PERMISSION_GRANTED)){
/*
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,Uri.parse("package:"+getPackageName()));
                startActivity(intent);
                finish();
                */
                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null)));
            }
        }

    }
    @Override
    protected void onPause(){
        super.onPause();
        myTTS.shutdown();

    }
}
