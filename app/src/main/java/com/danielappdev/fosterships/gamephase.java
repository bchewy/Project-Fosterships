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


// Reference to an image file in Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture.png");

// ImageView in your Activity
        ImageView imageView = findViewById(R.id.imageView4);

// Load the image using Glide
            Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);

    }
}
