package com.skripsi.herdiesadimarta.userapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skripsi.herdiesadimarta.userapp.R;

public class DaftarActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DaftarActivity.class.getSimpleName();
    private EditText EmailField;
    private EditText PassField;
    private EditText NamaField;
    private TextView loginError;

    private Button RegBtn;
    private Button toLogin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //initializing views
        NamaField = (EditText) findViewById(R.id.inputNama);
        EmailField = (EditText) findViewById(R.id.inputEmail);
        PassField = (EditText) findViewById(R.id.inputPass);

        toLogin = (Button) findViewById(R.id.textMoveLogin);
        RegBtn = (Button) findViewById(R.id.btnReg);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        RegBtn.setOnClickListener(this);
        toLogin.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
/*        databaseReference = firebaseDatabase.getReferenceFromUrl("https://skripsi-aa9a5.firebaseio.com/");*/
    }

    private void registerUser() {

        //getting name, phone, email and password from edit texts
        String nama = NamaField.getText().toString().trim();
        String email = EmailField.getText().toString().trim();
        String password  = PassField.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(nama)){
            Toast.makeText(this,"Masukkan nama anda",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Masukkan email anda",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Masukkan kata sandi anda", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password) || password.length() < 8)
        {
            Toast.makeText(this,"Minimal password 8 karakter", Toast.LENGTH_LONG).show();
            return;
        }

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Registration Suuccessful" + task.isSuccessful());
                        //checking if success
                        if(!task.isSuccessful()){
                            Toast.makeText(DaftarActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            userProfile();
                            Toast.makeText(DaftarActivity.this,"Create Account",Toast.LENGTH_LONG).show();
                            Log.d("TESTING", "Created Account");
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        progressDialog.dismiss();
                    }
                });

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

    }

    private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(NamaField.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("TESTING", "User profile update");
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {

        if(view == RegBtn){
            registerUser();
        }

        if(view == toLogin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}