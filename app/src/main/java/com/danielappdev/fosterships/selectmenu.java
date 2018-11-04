package com.danielappdev.fosterships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class selectmenu extends AppCompatActivity {

    Button btnJoin;
    Button btnBook;
    EditText inviteCode;
    TextView textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmenu);

        inviteCode = findViewById(R.id.txtInviteCode);
        btnJoin = findViewById(R.id.btnJoin);
        //btnBook = findViewById(R.id.btnBook);

        textview2 = findViewById(R.id.textView2);

        btnJoin.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        textview2.setText(inviteCode.getText().toString());
                    }
                }
        );

    }
}
