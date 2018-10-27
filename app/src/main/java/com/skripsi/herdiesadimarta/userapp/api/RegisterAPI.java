package com.skripsi.herdiesadimarta.userapp.api;

import com.skripsi.herdiesadimarta.userapp.models.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Herdies Adimarta on 02/08/2017.
 */

public interface RegisterAPI {
    @GET("view_agen_tunangan.php")
    Call<Value> view_tunangan();

    @GET("view_agen_wisuda.php")
    Call<Value> view_wisuda();

    @GET("view_agen_ultah.php")
    Call<Value> view_ultah();

    @GET("view_agen_pesta.php")
    Call<Value> view_pesta();

    @FormUrlEncoded
    @POST("search.php")
    Call<Value> search(@Field("search") String search);

}
