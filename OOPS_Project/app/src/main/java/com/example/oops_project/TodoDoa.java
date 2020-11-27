package com.example.oops_project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oops_project.Todo;

import java.util.List;

@Dao
public interface TodoDoa {

    @Insert
    void insert(Todo stock);

    @Update
    void update(Todo stock);

    @Delete
    void delete(Todo stock);

    @Query("DELETE FROM TODO_TABLE")
    void deleteAllTodos();

    @Query("SELECT * FROM TODO_TABLE WHERE title like :TodoText ORDER BY PRIORITYNUMBER DESC")
    LiveData<List<Todo>> getSearchedTodos(String TodoText);

    @Query("SELECT * FROM todo_table ORDER BY PRIORITYNUMBER DESC")
    LiveData<List<Todo>> getAllTodos();

//    void insert(Note high);
}
