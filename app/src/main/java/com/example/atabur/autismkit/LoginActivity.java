package com.example.atabur.autismkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSingup;
    private EditText emailLog;
    private EditText passwordlog;
    private TextView textViewSingup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
        //   finish();
      //    startActivity(new Intent(getApplicationContext(),CheckingActivity.class));
        }
        progressDialog = new ProgressDialog(this);


        buttonSingup = (Button) findViewById(R.id.loginLog);
        emailLog = (EditText) findViewById(R.id.emailLog);
        passwordlog = (EditText) findViewById(R.id.passwordLog);
        textViewSingup = (TextView) findViewById(R.id.toLogin);
        buttonSingup.setOnClickListener(this);
        textViewSingup.setOnClickListener(this);
    }
    private void userLogin(){
        final String email = emailLog.getText().toString().trim();
        final String password = passwordlog.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this,"Please Enter email",Toast.LENGTH_SHORT).show();
            //Stopping the function exection feature
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please Enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        //if validation are ok
        //we will first show progressbar
        progressDialog.setMessage("Login ....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      //  progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity
                            Toast.makeText(getApplicationContext(),"SuccessFully Login",Toast.LENGTH_SHORT).show();
                            finish();
                            Shared shared=new Shared(LoginActivity.this);
                            shared.setName(email);
                            shared.setPassword(password);
                           // Validation.validation(shared);
                            Intent intent =new Intent(LoginActivity.this,homeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                            //to change the boolean value as true
                           // shared.secondTime();
                        }
                        else{

                            Toast.makeText(LoginActivity.this,"Please Enter Currect Email and Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void onClick(View view){
        if(view == buttonSingup){
            userLogin();
        }
        if (view == textViewSingup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
