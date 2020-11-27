package com.example.oops_project;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.oops_project.TodoDatabase;
import com.example.oops_project.TodoDoa;
import com.example.oops_project.Todo;

import java.util.List;

public class TodoRepository  {

    private TodoDoa todoDoa;
    private LiveData<List<Todo>> allTodos;
    public TodoRepository(Application application3) {

        TodoDatabase database3 = TodoDatabase.getInstance(application3);
        todoDoa = database3.todoDoa();
        allTodos = todoDoa.getAllTodos();

    }

    public void insert(Todo todo) {
        new InsertNodeAsyncTask(todoDoa).execute(todo);
    }

    public void update(Todo todo) {
        new UpdateNodeAsyncTask(todoDoa).execute(todo);
    }

    public void delete(Todo todo) {
        new DeleteNodeAsyncTask(todoDoa).execute(todo);
    }

    public void deleteAllTodos() {
        new DeleteAllNodeAsyncTask(todoDoa).execute();
    }

    public LiveData<List<Todo>> getSearchedTodos(String TodoText){
        return todoDoa.getSearchedTodos(TodoText);
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    private static class InsertNodeAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDoa todoDoa;

        private InsertNodeAsyncTask(TodoDoa todoDoa) {
            this.todoDoa = todoDoa;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDoa.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateNodeAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDoa todoDoa;

        private UpdateNodeAsyncTask(TodoDoa todoDoa) {
            this.todoDoa = todoDoa;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDoa.update(todos[0]);
            return null;
        }
    }


    private static class DeleteNodeAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDoa todoDoa;

        private DeleteNodeAsyncTask(TodoDoa todoDoa) {
            this.todoDoa = todoDoa;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDoa.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllNodeAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDoa todoDoa;

        private DeleteAllNodeAsyncTask(TodoDoa todoDoa) {
            this.todoDoa = todoDoa;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDoa.deleteAllTodos();
            return null;
        }
    }
}