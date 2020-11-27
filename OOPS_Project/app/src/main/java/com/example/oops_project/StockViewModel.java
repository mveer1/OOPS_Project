package com.example.oops_project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oops_project.StockRepository;
import com.example.oops_project.Stock;

import java.util.List;

public class StockViewModel extends AndroidViewModel {

    private StockRepository repository;
    private LiveData<List<Stock>> allStocks;

    public StockViewModel(@NonNull Application application) {
        super(application);

        repository = new StockRepository(application);
        allStocks = repository.getAllStocks();
    }

    public void insert(Stock stock){
        repository.insert(stock);
    }

    public void update(Stock stock){
        repository.update(stock);
    }

    public void delete(Stock stock){
        repository.delete(stock);
    }

    public void deleteAllStocks(){
        repository.deleteAllStocks();
    }

    public LiveData<List<Stock>> getSearchedStocks(String StockText){
        return repository.getSearchedStocks(StockText);
    }
    public LiveData<List<Stock>> getAllStock(){
        return allStocks;
    }
}