package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReferenceTeams = database.getReference("Teams");
    EditText answerBox;
    Integer EventID;
    String Android_ID;
    Integer role;
    Button btnTryGuess;
    ImageView imageView;
    DatabaseReference EventRef;
    String Tname;
    boolean isImageFitToScreen;
    Integer runOnce = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        EventRef = database.getReference(String.valueOf("Events"));
        setContentView(R.layout.activity_gamephase);
        Tname = "Team banana";
        imageView = findViewById(R.id.imageViewgm2);
        answerBox = findViewById(R.id.answerBox);
        //Prep merge
        btnTryGuess = findViewById(R.id.btnGuess);
        //Get EventID
        Intent mIntent = getIntent();
        EventID = 3518;
        Round1();
        new CountDownTimer(5, 1) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                CheckStatus();
                CheckRound();
                start();
            }
        }.start();


        /*while(runOnce<1){//Only ever runs once because variable is 0 on creation
            if(runOnce<0){
                break;
            }
            else{
                //runOnce = LoadImageFromFirebase(runOnce);//Variable +=1 in loadImagefromFirebase method
            }


        }*/


        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer(defReferenceTeams, answerBox.getText().toString(), EventID);
                //Load with Glide
                LoadImageFromFirebase(); //Secretly only for testing right now!
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                fullScreen();
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }

            }
        });


    }



    public int getRole() {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Members").child(Android_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d("exist", "exist ");

                }
                //int makeshiftrole = dataSnapshot.child("role").getValue(Integer.class);
                mEditor = mPref.edit();
                //mEditor.putInt("Role",makeshiftrole);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.d("role", (role = mPref.getInt("Role",0)).toString());
        return role = mPref.getInt("Role",0);

    }


    public void LoadImageFromFirebase() {
        Log.d("old", "?");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
            .load(storageReference)
                .into(imageView);
}


    public void LoadImageFromFirebase(Integer role, Integer round) {
        String temprole = role.toString();
        Log.d("pic", String.valueOf(round));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child((String.valueOf(((round-1)*4)+role)+".jpg").replace("0",""));//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }




    public void CheckAnswer(final DatabaseReference reference, final String answer, final Integer eventIDCurrent) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean correct = false;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    int eventID = (ds.child("eventID").getValue(Integer.class));
                    String answerFire = ds.child("phase1Answer").getValue(String.class);
                    if (eventID == (eventIDCurrent) && answerFire.equals(answer)) {
                        correct = true;
                        break;
                    }
                }
                if (!correct) {
                    ShowDialog("Wrong answer.. try again!", "Please try again... want a hint? A hint pops up on your leader's screen every few minutes!");
                } else {
                    ShowDialog("Correct!", "Now you're waiting for your teammates to also input the answer! - To move on to the next phase!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Starts a Show Dialog prompt on the screen with title/text!
    private void ShowDialog(String title, String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(gamephase.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }



    public void fullScreen() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("tagimg", "Turning immersive mode mode off. ");
        } else {
            Log.i("tagimg", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }

    public void CheckStatus(){
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("Round").getValue(Integer.class).equals(snapshot.child("Score").getValue(Integer.class))){
                    EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(snapshot.child("Round").getValue(Integer.class)+1);
                    EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Members").child(Android_ID).child("Round").setValue(snapshot.child("Round").getValue(Integer.class)+1);
                    //LoadImageFromFirebase();

                    LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class)+1);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void CheckRound(){
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override

            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.child("Round").getValue(Integer.class) > (snapshot.child("Members").child(Android_ID).child("Round").getValue(Integer.class))){
                    EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Members").child(Android_ID).child("Round").setValue(snapshot.child("Round").getValue(Integer.class));
                    //LoadImageFromFirebase();

                    LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class));
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void Round1(){
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(1);
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override

            public void onDataChange(DataSnapshot snapshot) {
                LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}