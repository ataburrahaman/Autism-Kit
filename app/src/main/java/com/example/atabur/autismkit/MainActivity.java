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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSingin;
    private EditText editAge;
    private EditText editName;
    private ProgressDialog progressDialog;
   // private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

     //   progressBar = new ProgressBar(this);
       progressDialog = new ProgressDialog(this);

        editName =(EditText)findViewById(R.id.nameLo);
        editAge = (EditText)findViewById(R.id.ageLo) ;
        buttonRegister = (Button)findViewById(R.id.login);
        editTextEmail = (EditText)findViewById(R.id.emailLo);
        editTextPassword = (EditText)findViewById(R.id.passwordLo);
        textViewSingin = (TextView)findViewById(R.id.toSingup);


        buttonRegister.setOnClickListener(this);
        textViewSingin.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser()!=null){
            //hendel The alrady Login User
        }
    }

    private void registerUser(){
        final String email =editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editName.getText().toString().trim();
        final String age = editAge.getText().toString().trim();
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
        if(TextUtils.isEmpty(name)){
            // email is empty
            Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show();
            //Stopping the function exection feature
            return;
        }
        if(TextUtils.isEmpty(age)){
            // email is empty
            Toast.makeText(this,"Please Enter AGe",Toast.LENGTH_SHORT).show();
            // Stopping the function exection feature
            return;
        }
        //if validation are ok
        //we will first show progressbar
        progressDialog.setMessage("Register User....");
        // progressBar.setVisibility(View,VISIBLE);
        progressDialog.show();

      firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //User is successfully register and loged in
                            // we will start the profile activity here
                            //right now lets display a toast only
                            progressDialog.dismiss();

                            User user = new User(
                                    name,
                                    email,
                                    age
                            );
                            FirebaseDatabase.getInstance().getReference("Users")
                                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Register SuccessFully",Toast.LENGTH_SHORT).show();
                                        finish();
                                        Shared shared=new Shared(MainActivity.this);
                                        shared.setName(email);
                                        shared.setPassword(password);
                                        //startActivity(new Intent(MainActivity.this,CheckingActivity.class));
                                        Intent intent =new Intent(MainActivity.this,homeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else{
                                       // progressDialog.dismiss();
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(getApplicationContext(),"You are already Register",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), " Please Try again....", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                        }
                                    }
                                }
                            });





                       //   Intent i = new Intent(MainActivity.this,CheckingActivity.class);

                        //    startActivity(i);



                        }
                     else {
                       Toast.makeText(MainActivity.this,"Some Thing Wants To Wrong..",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,MainActivity.class));
                        }
                    }
                });

    }

    public void onClick(View view){
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSingin){
            //will open the singup activity
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
