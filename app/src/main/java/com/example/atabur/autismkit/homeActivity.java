package com.example.atabur.autismkit;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   /*   TextView Name;
      TextView fname,email;
       FirebaseAuth firebaseAuth;
       FirebaseUser user;
    DatabaseReference reference; */
    private CardView checkBot;
    private CardView gameBot;
    private TextView navUsername,navEmail;
    private DatabaseReference nameref,emailref;

    NavigationView navigationView1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
     /*  firebaseAuth =FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Name = (TextView) findViewById(R.id.NameView);
        fname = (TextView)findViewById(R.id.fname);
        email =(TextView)findViewById(R.id.profileName);
        reference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        fname.setText(user.getDisplayName());
        Name.setText(user.getEmail());
        email.setText(user.getEmail());*/

        navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.userName);
        navEmail =(TextView)headerView.findViewById(R.id.userEmail);
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        nameref= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("name");
        emailref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("email");

       // Toast.makeText(homeActivity.this, "Data store successfully :"+nameref, Toast.LENGTH_SHORT).show();
        nameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    String name = dataSnapshot.getValue(String.class);
                    navUsername.setText(name);
                }
                catch(Throwable e) {

                    Toast.makeText(homeActivity.this, "Error :" +e, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        emailref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email=dataSnapshot.getValue(String.class);
                navEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkBot = findViewById(R.id.checkBot);
        gameBot =  findViewById(R.id.gameBot);
        checkBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeActivity.this,LebelBordActivity.class));
            }
        });
        gameBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeActivity.this,ImageLevelShow.class));
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent=new Intent(homeActivity.this,profileActivity.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
            new Shared(homeActivity.this).removeUser();
            Intent intent=new Intent(homeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Autism Kit");
            String message = "\nYou Can Share this application\n\n";

            i.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(i, "Select one"));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*
    public void homeBot(View view){
        if(view == checkBot){
            startActivity(new Intent(this,LebelBordActivity.class));
        }
        if (view == gameBot){
            startActivity(new Intent(this,ImageLevelShow.class));
        }
    }
    */

}
