package com.danielappdev.fosterships;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class gamephase extends AppCompatActivity {
    ImageView imageview2;
    boolean isImageFitToScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamephase);
       // LoadImageFromFirebase();



        imageview2 = findViewById(R.id.imageViewgm2);
        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen){
                    isImageFitToScreen=false;
                    //  imageview2.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    imageview2.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    imageview2.setAdjustViewBounds(true);
                }

                else{
                    // imageview2.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    imageview2.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    imageview2.setScaleType(ImageView.ScaleType.FIT_XY);

                }
            }
        });
    }

}

//    public void LoadImageFromFirebase() {
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture.png");//hardcoded "picture.png"
//        ImageView imageView = findViewById(R.id.imageViewgm2);
//        Glide.with(getApplicationContext())
//                .load(storageReference)
//                .into(imageView);
//    }
