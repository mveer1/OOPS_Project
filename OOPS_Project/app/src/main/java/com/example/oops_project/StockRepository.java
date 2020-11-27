package com.example.oops_project;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.oops_project.StockDatabase;
import com.example.oops_project.StockDoa;
import com.example.oops_project.Stock;

import java.util.List;

public class StockRepository  {

    private StockDoa stockDoa;
    private LiveData<List<Stock>> allStocks;
    public StockRepository(Application application) {

        StockDatabase database = StockDatabase.getInstance(application);
        stockDoa = database.stockDoa();
        allStocks = stockDoa.getAllStocks();

    }

    public void insert(Stock stock) {
        new InsertNodeAsyncTask(stockDoa).execute(stock);
    }

    public void update(Stock stock) {
        new UpdateNodeAsyncTask(stockDoa).execute(stock);
    }

    public void delete(Stock stock) {
        new DeleteNodeAsyncTask(stockDoa).execute(stock);
    }

    public void deleteAllStocks() {
        new DeleteAllNodeAsyncTask(stockDoa).execute();
    }

    public LiveData<List<Stock>> getSearchedStocks(String StockText){
        return stockDoa.getSearchedStocks(StockText);
    }

    public LiveData<List<Stock>> getAllStocks() {
        return allStocks;
    }

    private static class InsertNodeAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDoa stockDoa;

        private InsertNodeAsyncTask(StockDoa stockDoa) {
            this.stockDoa = stockDoa;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDoa.insert(stocks[0]);
            return null;
        }
    }

    private static class UpdateNodeAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDoa stockDoa;

        private UpdateNodeAsyncTask(StockDoa stockDoa) {
            this.stockDoa = stockDoa;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDoa.update(stocks[0]);
            return null;
        }
    }


    private static class DeleteNodeAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDoa stockDoa;

        private DeleteNodeAsyncTask(StockDoa stockDoa) {
            this.stockDoa = stockDoa;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDoa.delete(stocks[0]);
            return null;
        }
    }

    private static class DeleteAllNodeAsyncTask extends AsyncTask<Void, Void, Void> {
        private StockDoa stockDoa;

        private DeleteAllNodeAsyncTask(StockDoa stockDoa) {
            this.stockDoa = stockDoa;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stockDoa.deleteAllStocks();
            return null;
        }
    }
}