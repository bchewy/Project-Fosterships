package com.danielappdev.fosterships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class gamephase3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamephase3);
        LoadImageFromFirebase();

    }
    public void LoadImageFromFirebase() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("mushroom/").child("rowTWOcolONE.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageView4);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }
}
