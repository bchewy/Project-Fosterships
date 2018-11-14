package com.danielappdev.fosterships;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class gamephase extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReferenceTeams = database.getReference("Teams");
    EditText answerBox;
    Integer eventID;
    Button btnTryGuess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamephase);

        answerBox = findViewById(R.id.answerBox);
        btnTryGuess = findViewById(R.id.btnGuess);
        Intent mIntent = getIntent();
        eventID = mIntent.getIntExtra("EventID", 0);

        LoadImageFromFirebase();
        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer(defReferenceTeams,answerBox.getText().toString(),eventID);
            }
        });


    }

    public void LoadImageFromFirebase() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("mushroom/").child("rowONEcolONE.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageView4);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }
    public void CheckAnswer(final DatabaseReference reference, final String answer, final Integer eventIDCurrent){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    int eventID = (ds.child("eventID").getValue(Integer.class));
                    //String answer = ds.child("")
                    if (eventID == (eventIDCurrent)) {
                        Log.d("tag4",String.valueOf(eventID));
                        break;
                    } else {
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
