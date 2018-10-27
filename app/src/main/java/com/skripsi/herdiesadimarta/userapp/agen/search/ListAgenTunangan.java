package com.skripsi.herdiesadimarta.userapp.agen.search;

/**
 * Created by Herdies Adimarta on 19/08/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;

import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.adapter.RecyclerViewTunangan;
import com.skripsi.herdiesadimarta.userapp.api.RegisterAPI;
import com.skripsi.herdiesadimarta.userapp.models.Agen;
import com.skripsi.herdiesadimarta.userapp.models.Value;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListAgenTunangan extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String URL = "http://192.168.100.6/makeup_db_skripsi/";
    private List<Agen> agen = new ArrayList<>();
    private RecyclerViewTunangan viewAdapter;

    @BindView(R.id.recycleView)RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_agen);

        ButterKnife.bind(this);
        viewAdapter = new RecyclerViewTunangan(this, agen);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        AmbilDataAgen();
    }

    @Override
    protected void onResume(){
        super.onResume();
        AmbilDataAgen();
    }

    private void AmbilDataAgen(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.view_tunangan();

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if(value.equals("1")) {
                    agen = response.body().getResult();
                    viewAdapter = new RecyclerViewTunangan(ListAgenTunangan.this, agen);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }

/*
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Cari Berdasarkan Nama Salon");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }
*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.search(newText);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(value.equals("1")) {
                    agen = response.body().getResult();
                    viewAdapter = new RecyclerViewTunangan(ListAgenTunangan.this, agen);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

        return true;
    }
}