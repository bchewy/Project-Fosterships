package com.danielappdev.fosterships;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.provider.Settings.Secure;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




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
