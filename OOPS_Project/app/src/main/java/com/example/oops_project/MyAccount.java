package com.example.oops_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyAccount extends AppCompatActivity {
    FirebaseFirestore fStore;
    TextView un, ph, em, pr;
    FirebaseAuth fAuth;
    String userId;
    int i = 1;
    String s= getString(i);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        un = (TextView) findViewById(R.id.username);
        ph = (TextView) findViewById(R.id.phone);
        em = (TextView) findViewById(R.id.email);
        pr = (TextView) findViewById(R.id.profession);

        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                un.setText(value.getString("username"));
                ph.setText(value.getString("phone"));
                em.setText(value.getString("email"));
                pr.setText(value.getString("profession"));
            }
        });
    }

    public void logout_user(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }
}