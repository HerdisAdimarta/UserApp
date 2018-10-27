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
import com.skripsi.herdiesadimarta.userapp.R;
import com.skripsi.herdiesadimarta.userapp.tabs.Home;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText EmailField;
    private EditText PassField;
    private TextView loginError;

    private Button LoginBtn;
    private Button toReg;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        mAuth= FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(mAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
        }

        //initializing views
        EmailField = (EditText) findViewById(R.id.logEmail);
        PassField = (EditText) findViewById(R.id.logPass);
        LoginBtn = (Button) findViewById(R.id.btnLogin);
        toReg  = (Button) findViewById(R.id.textMoveReg);

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        LoginBtn.setOnClickListener(this);
        toReg.setOnClickListener(this);
    }

    //method for user login
    private void userLogin(){
        String email = EmailField.getText().toString().trim();
        String password  = PassField.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //logging in the user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "login successful:" + task.isSuccessful());
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(!task.isSuccessful()){
                            Log.w("TESTING", "login with email: failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Email atau Kata Sandi Salah", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                            finish();
                            startActivity(i);
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == LoginBtn){
            userLogin();
        }

        if(view == toReg){
            finish();
            startActivity(new Intent(this, DaftarActivity.class));
        }
    }
}

