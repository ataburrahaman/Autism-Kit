package com.example.atabur.autismkit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class CheckingActivity extends AppCompatActivity {

   // EditText editText;

    private TextView question;
    SpeechRecognizer mspeechRecognizer;
    Intent mspeechRecognizerIntent;
    private TextToSpeech myTTS;
     DatabaseReference reference;
     int total=1;
     int correct = 0;
     int wrong =0;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);
        checkPermission();
        mspeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mspeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mspeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);


       // ConstraintLayout = (ConstraintLayout) findViewById(R.id.quView);



        question = (TextView) mConstraintLayout.findViewById(R.id.quView);

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
                }
                precessResult(matches.get(0));

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });


        initializeTextToSpeech();

        updateQuestion();
    }

    private void updateQuestion() {

        try {

            if (total > 26) {

            } else {
                reference = FirebaseDatabase.getInstance().getReference().child("Question").child("label1").child(String.valueOf(total));
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {

                           // Question questi = dataSnapshot.getValue(Question.class);
                            String name = dataSnapshot.getValue(String.class);

                           question.setText(name);
                            Toast.makeText(CheckingActivity.this, "Value : " + name, Toast.LENGTH_SHORT).show();
                        }
                        catch(Throwable e){
                                Toast.makeText(CheckingActivity.this, "Error : " +reference, Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError){

                        }

                });
            }
        }catch(Throwable e) {

            Toast.makeText(CheckingActivity.this, "Error : " +e, Toast.LENGTH_SHORT).show();

        }

    }

    private void precessResult(String command) {
        //Here Check Our Result User Voice What the user say;
    }




    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (myTTS.getEngines().size()==0){
                    Toast.makeText(CheckingActivity.this,"There is no TTS engine on your device",
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
