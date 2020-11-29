package com.example.oops_project.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oops_project.MainActivity;
import com.example.oops_project.R;

public class Alarm_Reminder extends AppCompatActivity implements View.OnClickListener {


    private int notificationId=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm__reminder);
        findViewById(R.id.B1).setOnClickListener(this);
        findViewById(R.id.B2).setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view)
    {
        EditText message = findViewById(R.id.message);
        TimePicker timePicker=findViewById(R.id.timepicker);

        Intent intent=new Intent(Alarm_Reminder.this,Alarm_Receiver.class);
        intent.putExtra("Notificationid",notificationId);
        intent.putExtra("Message",message.getText().toString());

        //pending intent
        PendingIntent alarmintent=PendingIntent.getBroadcast(
                Alarm_Reminder.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT
        );
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

        switch (view.getId())
        {
            case R.id.B1:            //Set Alarm
               int hour=timePicker.getCurrentHour();
               int minutes=timePicker.getCurrentMinute();

                Calendar startTime= Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,hour);
                startTime.set(Calendar.MINUTE,minutes);
                startTime.set(Calendar.SECOND,0);
                long alarmStartTime=startTime.getTimeInMillis();

                Toast.makeText(this,"Done!",Toast.LENGTH_SHORT).show();
                break;




            case R.id.B2:            //Cancel Reminder
               alarmManager.cancel(alarmintent);
                Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}