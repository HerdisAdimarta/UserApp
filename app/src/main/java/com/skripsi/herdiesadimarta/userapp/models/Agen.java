package com.skripsi.herdiesadimarta.userapp.models;

/**
 * Created by Herdies Adimarta on 02/08/2017.
 */

public class Agen {
    String id;
    String nama_lengkap;
    String hrg_tunangan;
    String hrg_wisuda;
    String hrg_ultah;
    String hrg_pesta;
    String email_agen;

    public String getId(){
        return id;
    }

    public String getNama_lengkap(){
        return nama_lengkap;
    }

    public String getHrg_tunangan(){
        return hrg_tunangan;
    }

    public String getHrg_wisuda(){
        return hrg_wisuda;
    }

    public String getHrg_ultah(){
        return hrg_ultah;
    }

    public String getHrg_pesta(){
        return hrg_pesta;
    }

    public String getEmail_agen(){
        return email_agen;
    }

}