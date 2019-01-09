package com.danielappdev.fosterships;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class normaluserwaitingscreen extends AppCompatActivity {

    //TextView tv_timer = findViewById(R.id.tv_timer);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normaluserwaitingscreen);

        ConstraintLayout rlayout = (ConstraintLayout) findViewById(R.id.normaluserswaitingscreenlayout);
        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),authentication.class);
                startActivity(intent);
            }
        });


        Button nexttip = (Button) findViewById(R.id.btnnextfact);
        nexttip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvnextfact =" A ball of glass will bounce higher than a ball of rubber.";
                TextView tvnextfact1 = findViewById(R.id.tvfact);
                tvnextfact1.setText(tvnextfact.toString());
            }
        });



        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                 /*String[] Roles = {"Player"};
                Random r = new Random();
                String playerrole = Roles[r.nextInt(Roles.length)];

                if (playerrole == "Player"){
                    mEditor = mPref.edit();


                    String android_id = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    mEditor.putString("AndroidID",android_id);
                    mEditor.commit();
                    eventID = mPref.getInt("EventID",0);
                    HashMap<String, Object> Member = new HashMap<>();
                    Member.put("Player1",android_id);
                    defReference.child(String.valueOf(eventID)).child("Teams").child("Team Banana").child("Members").updateChildren(Member);
                }*/
                Intent intent = new Intent(getApplicationContext(),authentication.class);
                startActivity(intent);
            }
        }.start();
    }
}
