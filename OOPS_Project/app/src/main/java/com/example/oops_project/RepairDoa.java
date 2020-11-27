package com.example.oops_project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oops_project.Stock;

import java.util.List;

@Dao
public interface RepairDoa {

    @Insert
    void insert(Repair repair);

    @Update
    void update(Repair repair);

    @Delete
    void delete(Repair repair);

    @Query("DELETE FROM REPAIR_TABLE")
    void deleteAllRepairs();

    @Query("SELECT * FROM REPAIR_TABLE WHERE title like :RepairText ORDER BY PRIORITYNUMBER DESC")
    LiveData<List<Repair>> getSearchedRepairs(String RepairText);

    @Query("SELECT * FROM repair_table ORDER BY PRIORITYNUMBER DESC")
    LiveData<List<Repair>> getAllRepairs();

//    void insert(Note high);
}
