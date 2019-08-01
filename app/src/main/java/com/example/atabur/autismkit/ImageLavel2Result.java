package com.example.atabur.autismkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImageLavel2Result extends AppCompatActivity {

    DatabaseReference databaseReference;

    private RecyclerView mBlogeList;
    private ImageView priviousPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_lavel2_result);


        databaseReference =FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ImageResult").child("label2");
        databaseReference.keepSynced(true);
        mBlogeList =(RecyclerView)findViewById(R.id.myRecycleview);
        mBlogeList.setHasFixedSize(true);
        mBlogeList.setLayoutManager(new LinearLayoutManager(this));

        priviousPage = findViewById(R.id.prePage);
        priviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageLavel2Result.this,ImageLevelShow.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Word,WordLavel1Result.WordViewHolde> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Word, WordLavel1Result.WordViewHolde>
                (Word.class,R.layout.showdatalist,WordLavel1Result.WordViewHolde.class,databaseReference) {
            @Override
            protected void populateViewHolder(WordLavel1Result.WordViewHolde viewHolder, Word model, int position) {

                viewHolder.setMDate(model.getDate());
                viewHolder.setMTime(model.getTime());
                viewHolder.setPridctionres(model.getPrediction());
                viewHolder.setCorrect(String.valueOf(model.getCorrect()));
                viewHolder.setIncorrect(String.valueOf(model.getIncorrect()));
                viewHolder.setTotalTime(String.valueOf(model.getTotalTime()));

            }
        };

        mBlogeList.setAdapter(firebaseRecyclerAdapter);
/*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot wordSnapshot : dataSnapshot.getChildren()){

                    Word word = wordSnapshot.getValue(Word.class);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

    }

    public static class WordViewHolde extends RecyclerView.ViewHolder
    {
        View mView;
        public WordViewHolde(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public void setMDate(String date){
            TextView post_date=(TextView)mView.findViewById(R.id.editDate);
            post_date.setText(date);
        }
        public void setMTime(String time){
            TextView post_time=(TextView)mView.findViewById(R.id.editTime);
            post_time.setText(time);
        }
        public void setTotalTime(String totalTime){
            TextView post_time=(TextView)mView.findViewById(R.id.totalTime);
            post_time.setText(totalTime);
        }
        public void setCorrect(String correct){
            TextView post_correct=(TextView)mView.findViewById(R.id.editCorrect);
            post_correct.setText(correct);
        }
        public void setIncorrect(String incorrect){
            TextView post_incorrect=(TextView)mView.findViewById(R.id.editWrong);
            post_incorrect.setText(incorrect);
        }
        public void setPridction(String pridction){
            TextView post_incorrect=(TextView)mView.findViewById(R.id.setPridction);
            post_incorrect.setText(pridction);
        }


    }
}
