package com.example.oops_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_info extends AppCompatActivity {

    TextView name, email;
    Button logout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        logout = findViewById(R.id.logout_button);
//        name = findViewById(R.id.user_name);
//        email = findViewById(R.id.user_email);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount!=null){
            Toast.makeText(this, "Already Signed In", Toast.LENGTH_SHORT);
        }else{
            startActivity(new Intent(this, Login.class));
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(user_info.this, MainActivity2.class));
            }
        });




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
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseUser!=null){
            firebaseAuth.signOut();
            startActivity(new Intent(user_info.this, MainActivity.class));
        }

    }
}