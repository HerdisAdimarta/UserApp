package com.skripsi.herdiesadimarta.userapp.booking.tunangan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.activity.MainMenuActivity;
import com.skripsi.herdiesadimarta.userapp.mail.SendMail;

public class Verifikasi extends AppCompatActivity implements View.OnClickListener {
    private EditText namaBooking, bookDate, bookTime, bookPeta, ketBooking, teleponBooking;
    private TextView totalBooking;
    private Button btnKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        Bundle b = getIntent().getExtras();
        //membuat obyek dari widget textview
        TextView  tanggal = (TextView) findViewById(R.id.viewTanggal);
        TextView  alamat = (TextView) findViewById(R.id.viewAlamat);
        TextView  pesan = (TextView) findViewById(R.id.viewPesan);
        TextView  nama = (TextView) findViewById(R.id.viewNama);
        TextView  handphone = (TextView) findViewById(R.id.viewHp);
        TextView  email = (TextView) findViewById(R.id.viewEmail);
        TextView  total = (TextView) findViewById(R.id.viewTotal);
        TextView  btnKirim = (Button) findViewById(R.id.btnSend);

         //menset nilai dari widget textview
        tanggal.setText  ("Tanggal dan Waktu Pemesanan:" + (" ") + b.getCharSequence("tanggal") + (", ") + (" ") + b.getCharSequence("waktu"));
        alamat.setText   ("Alamat:" + (" ") + b.getCharSequence("alamat"));
        pesan.setText    ("Keterangan:" + (" ") + b.getCharSequence("keterangan"));
        nama.setText     ("Atas Nama:" + (" ") + b.getCharSequence("nama"));
        handphone.setText("Nomor Handphone:" + (" ") + b.getCharSequence("telepon"));
        email.setText(b.getCharSequence("email"));
        total.setText("Total:" + (" ") + b.getCharSequence("total"));

/*        bookPeta = (EditText) findViewById(R.id.bookPeta);
        bookDate = (EditText) findViewById(R.id.bookTgl);
        bookTime = (EditText) findViewById(R.id.bookJam);
        namaBooking = (EditText) findViewById(R.id.bookNama);
        teleponBooking = (EditText) findViewById(R.id.bookTlp);
        ketBooking = (EditText) findViewById(R.id.bookPesan);
        totalBooking = (TextView) findViewById(R.id.viewTotal);
        btnKirim = (Button) findViewById(R.id.btnSend);*/

        btnKirim.setOnClickListener(this);
    }

    private void sendEmail(){
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        Bundle c = getIntent().getExtras();
        String email   = c.getCharSequence("email").toString().trim();
        String subject = ("Pesan Makeup Tunangan").toString().trim() + (" ")
                       + c.getCharSequence("total").toString().trim() + (",") + (" ")
                       + ("Atas Nama: ").toString().trim() + (" ")
                       + c.getCharSequence("nama").toString().trim();
        String message = ("Tanggal dan Jam :").toString().trim() + (" ")
                         + c.getCharSequence("tanggal").toString().trim() + (",") + (" ")
                         + c.getCharSequence("waktu").toString().trim() + ("\n")
                         + ("Alamat :").toString().trim() + (" ")
                         + c.getCharSequence("alamat").toString().trim() + ("\n")
                         + ("Keterangan :").toString().trim() + (" ")
                         + c.getCharSequence("keterangan").toString().trim() + ("\n")
                         + ("Nomor Telepon :") .toString().trim() + (" ")
                         + c.getCharSequence("telepon").toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }

}
