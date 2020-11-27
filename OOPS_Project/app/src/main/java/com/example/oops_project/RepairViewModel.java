package com.example.oops_project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oops_project.RepairRepository;
import com.example.oops_project.Repair;

import java.util.List;

public class RepairViewModel extends AndroidViewModel {

    private RepairRepository repository;
    private LiveData<List<Repair>> allRepairs;

    public RepairViewModel(@NonNull Application application) {
        super(application);

        repository = new RepairRepository(application);
        allRepairs = repository.getAllRepairs();
    }

    public void insert(Repair stock){
        repository.insert(stock);
    }

    public void update(Repair stock){
        repository.update(stock);
    }

    public void delete(Repair stock){
        repository.delete(stock);
    }

    public void deleteAllRepairs(){
        repository.deleteAllRepairs();
    }

    public LiveData<List<Repair>> getSearchedRepairs(String RepairText){
        return repository.getSearchedRepairs(RepairText);
    }
    public LiveData<List<Repair>> getAllRepairs(){
        return allRepairs;
    }
}