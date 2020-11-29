package com.example.oops_project.ui;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class Alarm_Receiver extends BroadcastReceiver {

    private static final String CHANNEL_ID="SAMPLE_CHANNEL";



    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
//Get message and ID from intent
        int notificationId=intent.getIntExtra("notificationId",0);
        String message=intent.getStringExtra("message");

        Intent mainintent=new Intent(context,Alarm_Reminder.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context,0,mainintent,0);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES .O)
        {
            //API 26 and above
            CharSequence channel_name="My Notification!";
            int importance=NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,channel_name,importance);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("TITLE")
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(notificationId,builder.build());
    }
}
