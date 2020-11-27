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
public interface StockDoa {

    @Insert
    void insert(Stock stock);

    @Update
    void update(Stock stock);

    @Delete
    void delete(Stock stock);

    @Query("DELETE FROM STOCK_TABLE")
    void deleteAllStocks();

    @Query("SELECT * FROM STOCK_TABLE WHERE title like :StockText ORDER BY PRIORITYNUMBER DESC")
    LiveData<List<Stock>> getSearchedStocks(String StockText);

    @Query("SELECT * FROM stock_table ORDER BY PRIORITYNUMBER DESC")
    LiveData<List<Stock>> getAllStocks();

//    void insert(Note high);
}
