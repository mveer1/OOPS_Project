package com.example.oops_project;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepairRepository  {

    private RepairDoa repairDoa;
    private LiveData<List<Repair>> allRepairs;
    public RepairRepository(Application application) {

        RepairDatabase database = RepairDatabase.getInstance(application);
        repairDoa = database.repairDoa();
        allRepairs = repairDoa.getAllRepairs();

    }

    public void insert(Repair repair) {
        new InsertNodeAsyncTask(repairDoa).execute(repair);
    }

    public void update(Repair repair) {
        new UpdateNodeAsyncTask(repairDoa).execute(repair);
    }

    public void delete(Repair repair) {
        new DeleteNodeAsyncTask(repairDoa).execute(repair);
    }

    public void deleteAllRepairs() {
        new DeleteAllNodeAsyncTask(repairDoa).execute();
    }

    public LiveData<List<Repair>> getSearchedRepairs(String RepairText){
        return repairDoa.getSearchedRepairs(RepairText);
    }

    public LiveData<List<Repair>> getAllRepairs() {
        return allRepairs;
    }

    private static class InsertNodeAsyncTask extends AsyncTask<Repair, Void, Void> {
        private RepairDoa repairDoa;

        private InsertNodeAsyncTask(RepairDoa stockDoa) {
            this.repairDoa = repairDoa;
        }

        @Override
        protected Void doInBackground(Repair... repairs) {
            repairDoa.insert(repairs[0]);
            return null;
        }
    }

    private static class UpdateNodeAsyncTask extends AsyncTask<Repair, Void, Void> {
        private RepairDoa repairDoa;

        private UpdateNodeAsyncTask(RepairDoa stockDoa) {
            this.repairDoa = repairDoa;
        }

        @Override
        protected Void doInBackground(Repair... repairs) {
            repairDoa.update(repairs[0]);
            return null;
        }
    }


    private static class DeleteNodeAsyncTask extends AsyncTask<Repair, Void, Void> {
        private RepairDoa repairDoa;

        private DeleteNodeAsyncTask(RepairDoa repairDoa) {
            this.repairDoa = repairDoa;
        }

        @Override
        protected Void doInBackground(Repair... repairs) {
            repairDoa.delete(repairs[0]);
            return null;
        }
    }

    private static class DeleteAllNodeAsyncTask extends AsyncTask<Void, Void, Void> {
        private RepairDoa repairDoa;

        private DeleteAllNodeAsyncTask(RepairDoa repairDoa) {
            this.repairDoa = repairDoa;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            repairDoa.deleteAllRepairs();
            return null;
        }
    }
}