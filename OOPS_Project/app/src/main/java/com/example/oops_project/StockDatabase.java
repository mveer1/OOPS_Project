package com.example.oops_project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.oops_project.Stock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Database(entities = {Stock.class}, version = 3)
public abstract class StockDatabase extends RoomDatabase {

    private static StockDatabase instance2;

    public abstract StockDoa stockDoa();

    public static synchronized StockDatabase getInstance(Context context) {
        if (instance2 == null) {
            instance2 = Room.databaseBuilder(context.getApplicationContext()
                    , StockDatabase.class, "stocks_database")
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
        private com.example.oops_project.StockDoa StockDoa;

        private PopulateDbAsyncTask(StockDatabase db) {
            StockDoa = db.stockDoa();
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
            StockDoa.insert(new Stock("Title 1", "Description 1", "High", 3, date, time));
            StockDoa.insert(new Stock("Title 2", "Description 2", "Medium", 2, date, time));
            StockDoa.insert(new Stock("Title 3", "Description 3", "Low", 1, date, time));
            return null;
        }
    }

}