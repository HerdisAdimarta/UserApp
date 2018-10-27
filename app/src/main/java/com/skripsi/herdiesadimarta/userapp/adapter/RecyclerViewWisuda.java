package com.skripsi.herdiesadimarta.userapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.models.Agen;
import com.skripsi.herdiesadimarta.userapp.booking.wisuda.Booking;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewWisuda extends RecyclerView.Adapter<RecyclerViewWisuda.ViewHolder> {
    private Context context;
    private List<Agen> agen;

    public RecyclerViewWisuda(Context context, List<Agen> agen) {
        this.context = context;
        this.agen= agen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Agen agn = agen.get(position);
        holder.textViewNama.setText(agn.getNama_lengkap());
        holder.textViewHargaWisuda.setText(agn.getHrg_wisuda());
        holder.textViewEmail.setText(agn.getEmail_agen());
    }

    @Override
    public int getItemCount() {
        return agen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textNama)TextView textViewNama;
        @BindView(R.id.textHarga)TextView textViewHargaWisuda;
        @BindView(R.id.textEmail)TextView textViewEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Booking.class);
            //Membuat obyek bundle
            Bundle b = new Bundle();

            //Menyisipkan tipe data String ke dalam obyek bundle
            b.putString("harga", textViewHargaWisuda.getText().toString());
            b.putString("email", textViewEmail.getText().toString());


            //Menambahkan bundle intent
            intent.putExtras(b);

            //memulai Activity kedua
            context.startActivity(intent);
        }
    }
}