package com.danielappdev.fosterships;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class normaluserwaitingscreen extends AppCompatActivity {

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
    }
}
