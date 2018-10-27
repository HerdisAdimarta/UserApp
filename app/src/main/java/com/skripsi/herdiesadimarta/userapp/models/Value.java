package com.skripsi.herdiesadimarta.userapp.models;

import java.util.List;

/**
 * Created by Herdies Adimarta on 02/08/2017.
 */

public class Value {
    String value;
    String message;
    List<Agen> result;

    public String getValue(){
        return value;
    }

    public String getMessage(){
        return message;
    }

    public List<Agen> getResult() {
        return result;
    }
}
