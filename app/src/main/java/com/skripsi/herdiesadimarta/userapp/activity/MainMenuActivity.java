package com.skripsi.herdiesadimarta.userapp.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.tabs.Akun;
import com.skripsi.herdiesadimarta.userapp.tabs.Home;
import com.skripsi.herdiesadimarta.userapp.tabs.Info;

public class MainMenuActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, Home.class);//content pada tab yang akan kita buat
        spec = tabhost.newTabSpec("beranda").setIndicator("Beranda",null).setContent(intent);//mengeset nama tab dan mengisi content pada menu tab anda.
        tabhost.addTab(spec);//untuk membuat tabbaru disini bisa diatur sesuai keinginan anda

        intent = new Intent().setClass(this, Info.class);
        spec = tabhost.newTabSpec("info").setIndicator("Info",null).setContent(intent);
        tabhost.addTab(spec);

        intent = new Intent().setClass(this, Akun.class);
        spec = tabhost.newTabSpec("akun saya").setIndicator("Akun Saya",null).setContent(intent);
        tabhost.addTab(spec);

    }
}