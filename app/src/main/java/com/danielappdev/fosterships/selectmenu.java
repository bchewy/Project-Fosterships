package com.danielappdev.fosterships;

import android.content.Intent;
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
        Log.d("selectmenu", "runs on selectmenu");
        inviteCode = findViewById(R.id.txtInviteCode);
        btnJoin = findViewById(R.id.btnPlayer1);
        btnBook = findViewById(R.id.btnBook);
        //btnBook = findViewById(R.id.btnBook);

        textview2 = findViewById(R.id.textView2);

       btnJoin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            String codeinput =inviteCode.getText().toString();
            //need to check database and see if codeinput is inside
               // if code input is not, print out invalid code
                   Intent intent = new Intent(getApplicationContext(), normaluserwaitingscreen.class);
                   startActivity(intent);


           }
       });
       btnBook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
               startActivity(intent);
           }
       });

    }


}
