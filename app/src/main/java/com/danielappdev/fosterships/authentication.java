package com.danielappdev.fosterships;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        ConstraintLayout CS_Auth = findViewById(R.id.CS_Auth);
        //Button authbutton = findViewById(R.id.)
        final EditText authcode = findViewById(R.id.et_auth);
        String authcodetostring = authcode.toString();

        CS_Auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (authcode.equals("0506")){
                  Intent intent = new Intent(getApplicationContext(), gamephase3.class);
                  startActivity(intent);
              }

            }
        });
    }
}
