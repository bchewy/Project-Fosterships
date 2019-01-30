package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import android.widget.Toast;

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
    TextView TxtViewname;
    TextView T_Round;
    Button btnTryGuess;
    ImageView imageView;
    DatabaseReference EventRef;
    String Tname;
    Integer Round;
    Integer Round2;
    boolean isImageFitToScreen;
    Integer runOnce = 0;
    Integer makeshiftRound;
    String Hints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        EventRef = database.getReference(String.valueOf("Events"));
        setContentView(R.layout.activity_gamephase);
        imageView = findViewById(R.id.imageViewgm2);
        answerBox = findViewById(R.id.answerBox);
        TxtViewname  = findViewById(R.id.txtTeamname);
        T_Round = findViewById(R.id.TV_Round);
        Log.d("name", getTeam());
        Android_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //just checking to see if method correctly gets round
        //getRound(Tname);
        //Prep merge
        btnTryGuess = findViewById(R.id.btnGuess);
        getRound(getTeam());

        Intent mIntent = getIntent();
        EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot s1 : snapshot.getChildren()) {
                    if (s1.child("Members").child(Android_ID).exists()) {
                        Round1(String.valueOf(s1.child("TeamName").getValue()));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //getRole();
        // checks frequently
        new CountDownTimer(5, 1) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                /*EventRef.child(String.valueOf(EventID)).child("Teams").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot s1 : snapshot.getChildren()) {
                            if (s1.child("Members").child(Android_ID).exists()) {
                                CheckStatus(String.valueOf(s1.child("TeamName").getValue()));
                                RefreshPage(String.valueOf(s1.child("TeamName").getValue()));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                */
              //  CheckStatus(getTeam());

               RefreshPage(getTeam());
                start();
            }
        }.start();



        btnTryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Answer = String.valueOf(answerBox.getText());
                //DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
                CheckAnswer(Answer, getRound2());

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
/*
Toast.makeText(getActivity(), "This is my Toast message!",
    Toast.LENGTH_LONG).show();

*/


    public String getTeam()
    {
        Tname = mPref.getString("TeamName", "Default");
        return Tname;
    }

    public String getHints(final Integer Round)
    {
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
        EventRef.child(String.valueOf(EventID)).child("pictures").child("gamephase"+Round.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Hints =dataSnapshot.child("hints").getValue().toString();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return Hints;
    }




    public void RefreshPage(String Tname){
        Android_ID = mPref.getString("AndroidID","default");
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

    //initializes round to one then loads image from firebase based on role. 
    //method to load first image from firebase based on android IDs role and round
    public void Round1(String Tname){
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


/*
    public Integer getRound()
    {
        Round = Integer.parseInt(T_Round.toString());
        mEditor = mPref.edit();
        mEditor.putInt("Round", Round);
        mEditor.commit();
        Round= mPref.getInt("Round", Round);
        return Round;
    }
*/



    void getRound(String Tname) {
        Android_ID = mPref.getString("AndroidID","default");
        EventID = mPref.getInt("EventID",0);
        DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
      // Log.d("Round", "reached");
        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   Round = dataSnapshot.child("Round").getValue(Integer.class);
                   T_Round.setText(Round);
                    Log.d("Round", Round.toString());

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
   }


   public Integer getRound2()
   {
       return (Round2= Integer.parseInt(T_Round.toString()));
   }

/*
   public void SetTVRound(String Tname)
   {
       Android_ID = mPref.getString("AndroidID","default");
       EventID = mPref.getInt("EventID",0);
       DatabaseReference EventRef = database.getReference(String.valueOf("Events"));
       // Log.d("Round", "reached");
       EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   T_Round.setText(Round);
                //   Log.d("Round", Round.toString());
               }

           }
           @Override
           public void onCancelled(DatabaseError databaseError) {
           }
       });
   }
*/






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
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/").child("event_pics/").child(round.toString()+"/").child((String.valueOf(role.toString())));
        ImageView imageView = findViewById(R.id.imageViewgm2);
        Glide.with(getApplicationContext())
                .load(storageReference)
                .into(imageView);
    }


    // EventRef = database.getReference(String.valueOf("Events"));
//method to check if answer is equal to "gamephase + round" answer
    public void CheckAnswer(final String answer, final Integer Round) {

        EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean correct = false;
                for (DataSnapshot ds : snapshot.getChildren()) {
                   // int eventID = (ds.child("eventID").getValue(Integer.class));
                    //string round
                    String FirebaseAnswer = ds.child("gamephase"+Round.toString()).child("answers").getValue(String.class);
                    if  (FirebaseAnswer.equals(answer)) {
                        //increment score.
                       EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Score").setValue(snapshot.child("Score").getValue(Integer.class)+1);
                        correct = true;
                        break;
                    }
                }
                if (!correct) {
                    ShowDialog("Wrong answer.. try again!", "Please try again... want a hint? "+getHints(getRound2()));
                } else {
                    //ShowDialog("Correct!", "Now you're waiting for your teammates to also input the answer! - To move on to the next phase!");
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



    // if score is same as round then plus round by 1 and load next image based on the users android id
    public void CheckStatus(final String Tname){
        EventID = mPref.getInt("EventID",0);
        Android_ID = mPref.getString("AndroidID","default");
        EventRef.child(String.valueOf(EventID)).child("Teams").child("Team banana").addListenerForSingleValueEvent(new ValueEventListener() {//Single data load
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()){

                if(snapshot.child("Round").getValue(Integer.class).equals(snapshot.child("Round").getValue(Integer.class))) {
                    EventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Round").setValue(snapshot.child("Round").getValue(Integer.class) + 1);
                    //ventRef.child(String.valueOf(EventID)).child("Teams").child(Tname).child("Members").child(Android_ID).child("Round").setValue(snapshot.child("Round").getValue(Integer.class)+1);
                    //LoadImageFromFirebase();

                  LoadImageFromFirebase(snapshot.child("Members").child(Android_ID).child("role").getValue(Integer.class), snapshot.child("Round").getValue(Integer.class)+1);
                }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }





}