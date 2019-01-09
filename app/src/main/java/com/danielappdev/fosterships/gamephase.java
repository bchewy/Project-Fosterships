package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
<<<<<<< HEAD
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
=======
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
>>>>>>> b925817596f25f1155d164504cad48e732ed747e
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
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
    DatabaseReference defReference = database.getReference("Events");
<<<<<<< HEAD
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
=======
    String Android_ID;
>>>>>>> b925817596f25f1155d164504cad48e732ed747e
    EditText answerBox;
    Integer EventID;
    Button btnTryGuess;
    ImageView imageView;
    boolean isImageFitToScreen;
    Integer runOnce = 0;
    Integer role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamephase);
        imageView = findViewById(R.id.imageViewgm2);
        answerBox = findViewById(R.id.answerBox);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        //Prep merge
<<<<<<< HEAD
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
=======
        int eventID;
>>>>>>> b925817596f25f1155d164504cad48e732ed747e
        btnTryGuess = findViewById(R.id.btnGuess);
        //Get EventID
        Intent mIntent = getIntent();
        eventID = mIntent.getIntExtra("EventID", 0);
<<<<<<< HEAD
        LoadImageFromFirebase();
        new CountDownTimer(2, 1) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                final String TeamName = mPref.getString("TeamName","default");
                defReference.child(String.valueOf(eventID)).child("Teams").child(TeamName).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.child("").getValue(Integer.class).equals(snapshot.child("").getValue(Integer.class))){
                            defReference.child(String.valueOf(eventID)).child("Teams").child(TeamName).child("Round").setValue(snapshot.getValue(Integer.class)+1);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }.start();
     /*  while(runOnce<1){//Only ever runs once because variable is 0 on creation
           if(runOnce<0){
               break;
           }
           else{
               runOnce = LoadImageFromFirebase(runOnce);//Variable +=1 in loadImagefromFirebase method
           }
       }*/
=======
        //LoadImageFromFirebase();
        getRole();

      /*  while(runOnce<1){//Only ever runs once because variable is 0 on creation
            if(runOnce<0){
                break;
            }
            else{
                runOnce = LoadImageFromFirebase(runOnce);//Variable +=1 in loadImagefromFirebase method
            }
        }*/
>>>>>>> b925817596f25f1155d164504cad48e732ed747e


        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckAnswer(defReferenceTeams, answerBox.getText().toString(), eventID);
                //Load with Glide
                LoadImageFromFirebase(runOnce); //Secretly only for testing right now!
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


<<<<<<< HEAD


    // DatabaseReference defReferenceTeams = database.getReference("Teams");
    public void LoadImageFromFirebase() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
=======
    //need to see where your ID is in
    //how do i pull role where id in firebase is actual androidID
    //if role is 1 pull 1. If role is 2 pull 2
    //get event id from the user, using shared preferences
    //need to get role from firebase. Input in role into LoadImageFromFirebase(role)
    //convert role to string
    //use  for (DataSnapshot s1 : dataSnapshot.getChildren()) to obtain role where android ID = _
    //then LoadImageFromFirebase(role)




    //added custom path to pull rhino pic
    // DatabaseReference defReferenceTeams = database.getReference("Teams");


    //problem, tname is default

    public int getRole() {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        String Tname = mPref.getString("TeamName","Default");
        Log.d("name", Tname);
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


    public void LoadImageFromFirebase(Integer role) {
        String temprole = role.toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child(temprole);//hardcoded "picture.png"
>>>>>>> b925817596f25f1155d164504cad48e732ed747e
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }

<<<<<<< HEAD

    public Integer LoadImageFromFirebase(Integer runOnce) {
=======
   /* public Integer LoadImageFromFirebase(Integer runOnce) {
>>>>>>> b925817596f25f1155d164504cad48e732ed747e
        runOnce+=1;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child("1/").child("1.jpg");//hardcoded "picture.png"
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
<<<<<<< HEAD
        return runOnce;
=======
     return runOnce;
    }*/



    //public void CheckAnswer(final DatabaseReference defReference,final String answer,int score)
    {

>>>>>>> b925817596f25f1155d164504cad48e732ed747e
    }
    //public void CheckAnswer(final DatabaseReference defReference,final String answer,int score)
    {

    }

    //  DatabaseReference defReferenceTeams = database.getReference("Teams");
    //public void CheckAnswer(final DatabaseReference reference, final String answer, final Integer eventIDCurrent)
 /*  public void CheckAnswer(final DatabaseReference defReference,final String answer,int score) {
       defReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot snapshot) {
               Boolean correct = false;
               for (DataSnapshot ds : snapshot.getChildren()) {
                   //int eventID = (ds.child("eventID").getValue(Integer.class));
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
   }*/



<<<<<<< HEAD
=======
    //  DatabaseReference defReferenceTeams = database.getReference("Teams");
    //public void CheckAnswer(final DatabaseReference reference, final String answer, final Integer eventIDCurrent)
  /*  public void CheckAnswer(final DatabaseReference defReference,final String answer,int score) {
        defReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean correct = false;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //int eventID = (ds.child("eventID").getValue(Integer.class));
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
>>>>>>> b925817596f25f1155d164504cad48e732ed747e


<<<<<<< HEAD
=======
            }
        });
    }*/





>>>>>>> b925817596f25f1155d164504cad48e732ed747e

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



}