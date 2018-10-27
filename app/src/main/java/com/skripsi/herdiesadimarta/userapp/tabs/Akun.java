package com.skripsi.herdiesadimarta.userapp.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.activity.LoginActivity;

public class Akun extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail, textViewUserName, btnEdit, btnChPass;
    private Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akun);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }



        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.email);
        textViewUserName = (TextView) findViewById(R.id.nama);
        btnEdit = (TextView) findViewById(R.id.edit);
        buttonLogout = (Button) findViewById(R.id.btnLogOut);

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            //displaying logged in user name
            textViewUserEmail.setText(user.getEmail());
            textViewUserName.setText(user.getDisplayName());
        }

        //adding listener to button
        buttonLogout.setOnClickListener(this);

/*        btnChPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent chPassIntent = new Intent(AccountActivity.this, EditPasswordActivity.class);
                startActivity(chPassIntent);
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}