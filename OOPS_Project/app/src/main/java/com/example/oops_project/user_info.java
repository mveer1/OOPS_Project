package com.example.oops_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_info extends AppCompatActivity {

    EditText contact, job;
    public String profession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//
//            name = (TextView)findViewById(R.id.user_name);
//            email = (TextView)findViewById(R.id.user_email);
//
//            name.setText(personName);
//            email.setText(personEmail);
//        }
    }

    public void toMain(View view) {


        job = findViewById(R.id.editTextProfession);

        profession = job.getText().toString();

        contact = findViewById(R.id.editTextPhone);
        String number = contact.getText().toString();
        if(number.length()==10 && number.matches("[0-9]*")){
            Intent intent = new Intent(getApplicationContext(), verificationpage.class);
            intent.putExtra("profession", profession);
            startActivity(intent);
        }else{
            Log.d("User_info", "Invalid Phone Number");
            Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}