package com.example.oops_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
    }

    public void getUserData(){

        Log.w("Login", "Get User Data Function");

        EditText t1 = (EditText)findViewById(R.id.editTextTextPersonName2);
        EditText t2 = (EditText)findViewById(R.id.editTextTextEmailAddress);
        EditText t3 = (EditText) findViewById(R.id.editTextPhone);
        EditText t4 = (EditText)findViewById(R.id.editTextProfession);
        EditText t5 = (EditText)findViewById(R.id.editTextTextPassword);
        EditText t6 = (EditText)findViewById(R.id.editTextTextPassword2);

        String username = t1.getText().toString();
        String email = t2.getText().toString();
        String contact = t3.getText().toString();
        String profession = t4.getText().toString();
        String password = t5.getText().toString();
        String confirm_password  = t6.getText().toString();

        if(username==null | email ==null |contact==null |profession==null | password ==null |confirm_password==null){
            Log.w("Login", "Some Fields Null");
            Toast.makeText(this, "Please Fill all fields", Toast.LENGTH_SHORT);
        }
        else if(password.compareTo(confirm_password) != 0){
            Log.w("Login", "Password Mismatch");
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT);
        }
        else{
            Log.w("Login", "All Fields filled");
            createAccount(email, password);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI()
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void register_user(View view) {
        Log.w("Login", "Register User Function");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.w("Login", "ID Exists");
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }else{
            Log.w("Login", "Create New ID");
            getUserData();
        }
    }
}