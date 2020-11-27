package com.example.oops_project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.oops_project.Repair;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Database(entities = {Repair.class}, version = 3)
public abstract class RepairDatabase extends RoomDatabase {

    private static RepairDatabase instance2;

    public abstract RepairDoa repairDoa();

    public static synchronized RepairDatabase getInstance(Context context) {
        if (instance2 == null) {
            instance2 = Room.databaseBuilder(context.getApplicationContext()
                    , RepairDatabase.class, "repair_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance2;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance2).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private com.example.oops_project.RepairDoa RepairDoa;

        private PopulateDbAsyncTask(RepairDatabase db) {
            RepairDoa = db.repairDoa();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Calendar calendar = Calendar.getInstance();
            //String currentDate  = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

//            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//            String result = currentDate+", "+sdf.format(Calendar.getInstance().getTime());
//            System.out.println(result);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String date = dateFormat.format(calendar.getTime());
            String ntime = timeFormat.format(calendar.getTime());
            String time = ntime.replace("am", "AM").replace("pm", "PM");
            RepairDoa.insert(new Repair("Title 1", "Description 1", "Appliance Repair", 4, date, time));
            RepairDoa.insert(new Repair("Title 2", "Description 3", "Plumber", 2, date, time));
            RepairDoa.insert(new Repair("Title 3", "Description 2", "Electrician", 3, date, time));
            RepairDoa.insert(new Repair("Title 4", "Description 3", "Carpenter", 4, date, time));

            return null;
        }
    }

}