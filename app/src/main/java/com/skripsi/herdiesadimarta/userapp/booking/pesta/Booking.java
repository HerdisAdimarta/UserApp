package com.skripsi.herdiesadimarta.userapp.booking.pesta;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.activity.MainMenuActivity;

import java.util.Calendar;
import java.util.Date;

public class Booking extends Activity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;

    // konstanta untuk mendeteksi hasil balikan dari place picker
    private int PLACE_PICKER_REQUEST = 1;
    private int mYear, mMonth, mDay, mHour, mMinute;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    private EditText namaBooking, bookDate, bookTime, bookPeta, ketBooking, teleponBooking;
    private TextView viewHarga, viewEmail;
    private Button tombolProses, btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle b = getIntent().getExtras();
        Bundle c = getIntent().getExtras();

        firebaseAuth = FirebaseAuth.getInstance();

        bookPeta = (EditText) findViewById(R.id.bookPeta);
        bookDate = (EditText) findViewById(R.id.bookTgl);
        bookTime = (EditText) findViewById(R.id.bookJam);
        namaBooking = (EditText) findViewById(R.id.bookNama);
        teleponBooking = (EditText) findViewById(R.id.bookTlp);
        ketBooking = (EditText) findViewById(R.id.bookPesan);
        tombolProses = (Button) findViewById(R.id.btnProses);

        viewHarga = (TextView) findViewById(R.id.textHarga);
        viewEmail = (TextView) findViewById(R.id.textEmail);
        //menset nilai dari widget textview
        viewHarga.setText(b.getCharSequence("harga"));
        viewEmail.setText(b.getCharSequence("email"));

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            //displaying logged in user name
            namaBooking.setText(user.getDisplayName());
        }

        //datepicker
        bookDate = (EditText) findViewById(R.id.bookTgl);
        bookDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar calendarDate = Calendar.getInstance();
        mYear = calendarDate.get(Calendar.YEAR);
        mMonth = calendarDate.get(Calendar.MONTH);
        mDay = calendarDate.get(Calendar.DAY_OF_MONTH);
        updateDisplayDate();

        //timepicker
        bookTime = (EditText) findViewById(R.id.bookJam);
        bookTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        // get the current time
        final Calendar calendarTime = Calendar.getInstance();
        mHour = calendarTime.get(Calendar.HOUR_OF_DAY);
        mMinute = calendarTime.get(Calendar.MINUTE);

        // display the current date
        updateDisplayTime();

        //memanggil google maps
        bookPeta = (EditText) findViewById(R.id.bookPeta);
        bookPeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuat Intent untuk Place Picker
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(Booking.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        //blokir edit text supaya tidak bisa di edit
        namaBooking.setTag(namaBooking.getKeyListener());
        namaBooking.setKeyListener(null);

        /*alamatText.setTag(alamatText.getKeyListener());*/


        //tombol kembali
        btnBack = (Button) findViewById(R.id.btnKembali);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent signUpIntent = new Intent(Booking.this, MainMenuActivity.class);
                startActivity(signUpIntent);
            }
        });

        tombolProses.setOnClickListener(this);
    }

    //mendapatkan peta
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(Booking.this, data);
                bookPeta.setText(place.getAddress());
            }
        }
    }

    //datepicker
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog da = new DatePickerDialog(this, mDateSetListener,
                        mYear, mMonth, mDay);
                Calendar c = Calendar.getInstance();

                c.add(Calendar.DATE, 1);
                Date newDate = c.getTime();
                da.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
                return da;
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }

    //
    private void updateDisplayDate() {
        bookDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
    }
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplayDate();
                }
            };

    // updates the time we display in the TextView
    private void updateDisplayTime() {
        bookTime.setText(
                new StringBuilder()
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));
    }

    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplayTime();
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    private void sendVerifikasi() {
        Intent intent = new Intent(getApplicationContext(), Verifikasi.class);
        Bundle a = new Bundle();

        String tanggal = bookDate.getText().toString().trim();
        String alamat = bookPeta.getText().toString().trim();
        String waktu = bookTime.getText().toString().trim();
        String keterangan  = ketBooking.getText().toString().trim();
        String namalengkap  = namaBooking.getText().toString().trim();
        String telepon  = teleponBooking.getText().toString().trim();

        if(TextUtils.isEmpty(tanggal)){
            Toast.makeText(this,"Masukkan tanggal pemesanan anda",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(waktu)){
            Toast.makeText(this,"Masukkan jam pemesanan anda",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(alamat)){
            Toast.makeText(this,"Masukkan alamat anda",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(alamat) || alamat.length() < 14)
        {
            Toast.makeText(this,"Masukkan alamat rumah anda dengan lengkap", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(keterangan)){
            Toast.makeText(this,"Masukkan keterangan anda", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(namalengkap)){
            Toast.makeText(this,"Masukkan nama lengkap anda", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(telepon)){
            Toast.makeText(this,"Masukkan nomor handphone anda", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(telepon) || telepon.length() < 10)
        {
            Toast.makeText(this,"Masukkan nomor handphone anda minimal 11 angka", Toast.LENGTH_LONG).show();
            return;
        }

        a.putString("tanggal", bookDate.getText().toString());
        a.putString("waktu", bookTime.getText().toString());
        a.putString("alamat", bookPeta.getText().toString());
        a.putString("keterangan", ketBooking.getText().toString());
        a.putString("nama", namaBooking.getText().toString());
        a.putString("telepon", teleponBooking.getText().toString());
        a.putString("total", viewHarga.getText().toString());
        a.putString("email", viewEmail.getText().toString());

        //Menambahkan bundle intent
        intent.putExtras(a);

        //memulai Activity kedua
        startActivity(intent);

/*
        //Getting content for email
        String email   = ("aammy197@yahoo.com").toString().trim();
        String subject = ("Pesan Makeup Ulang Tahun Rp 250 ribu, Atas Nama: ").toString().trim() + (" ") + namaBooking.getText().toString().trim();
        String message = ("Alamat :") + (" ") + alamatText.getText().toString().trim() + ("\n") +
                ("Keterangan :") + (" ") + ketBooking.getText().toString().trim() + ("\n") +
                ("Nomor Telepon :") + (" ") + teleponBooking.getText().toString().trim() +  ("\n") +
                ("Tanggal :") + (" ") + tglBooking.getText().toString().trim() +  ("\n") +
                ("Jam :") + (" ") + jamBooking.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
*/
    }

    @Override
    public void onClick(View v) {
        sendVerifikasi();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}