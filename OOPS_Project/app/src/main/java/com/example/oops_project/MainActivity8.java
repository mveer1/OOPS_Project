package com.example.oops_project;

import android.os.Bundle;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity8 extends AppCompatActivity {

    RecyclerView myRecyclerView;


    String s1[],s2[];
    int image[]={R.drawable.gitlogo,R.drawable.gitlogo,R.drawable.gitlogo,R.drawable.gitlogo};











//            fab.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView link;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        myRecyclerView=findViewById(R.id.myRecyclerView);

        s1=getResources().getStringArray(R.array.team);
        s2=getResources().getStringArray(R.array.teamid);

        recycleAdapter RecycleAdapter=new recycleAdapter(this,s1,s2,image);
        myRecyclerView.setAdapter(RecycleAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        link = findViewById(R.id.textView12);
        link.setMovementMethod(LinkMovementMethod.getInstance());

    }
}