package com.skripsi.herdiesadimarta.userapp.tabs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.into.PestaActivity;
import com.skripsi.herdiesadimarta.userapp.into.TunanganActivity;
import com.skripsi.herdiesadimarta.userapp.into.UltahActivity;
import com.skripsi.herdiesadimarta.userapp.into.WisudaActivity;

public class Home extends Activity {

    Button btnEngagement,btnParty, btnGraduation, btnBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //Mendirect ke menu booking tunangan
        Button btnEngagement = (Button) findViewById(R.id.btnEngagement);
        btnEngagement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), TunanganActivity.class);
                startActivity(i);
            }
        });

        //Mendirect ke menu booking wisuda
        Button btnGraduation = (Button) findViewById(R.id.btnGraduation);
        btnGraduation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), WisudaActivity.class);
                startActivity(i);
            }
        });

        //Mendirect ke menu booking Pesta
        Button btnParty = (Button) findViewById(R.id.btnParty);
        btnParty.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), PestaActivity.class);
                startActivity(i);
            }
        });

        //Mendirect ke menu booking Ulangtahun
        Button btnBirthday = (Button) findViewById(R.id.btnBirthday);
        btnBirthday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), UltahActivity.class);
                startActivity(i);
            }
        });

    }


}
