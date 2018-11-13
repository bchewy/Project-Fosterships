package com.danielappdev.fosterships;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class gamephase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamephase);
        LoadImageFromFirebase();


    }

    public void LoadImageFromFirebase() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture.png");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageView4);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }
}
