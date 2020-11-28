package com.example.oops_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity3 extends AppCompatActivity {

    RecyclerView myRecyclerView;

    String s1[],s2[];
    int image[]={R.drawable.logo2,R.drawable.logo2,R.drawable.logo2,R.drawable.logo2};

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TextView link;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        s1=getResources().getStringArray(R.array.team);
        s2=getResources().getStringArray(R.array.teamid);
        myRecyclerView=findViewById(R.id.myRecyclerView);

        //link = findViewById(R.id.textView12);
        //link.setMovementMethod(LinkMovementMethod.getInstance());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_stocks, R.id.nav_todo, R.id.maintainance,R.id.nav_HomeAppliance,R.id.nav_notes,R.id.nav_myAccount,R.id.nav_contactUs)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        recycleAdapter RecycleAdapter=new recycleAdapter(this,s1,s2,image);
        myRecyclerView.setAdapter(RecycleAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity3, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.nav_notes:
                startActivity(new Intent(getApplicationContext(), MainActivity4.class));
                break;
            case R.id.maintainance:
                startActivity(new Intent(getApplicationContext(), maintainance.class));
                break;
            case R.id.nav_contactUs:
                startActivity(new Intent(getApplicationContext(), aboutus.class));
                break;
            case R.id.nav_stocks:
                startActivity(new Intent(getApplicationContext(), MainActivity5.class));
                break;
            case R.id.nav_todo:
                startActivity(new Intent(getApplicationContext(), MainActivity6.class));
        }
        return super.onOptionsItemSelected(item);

    }
}