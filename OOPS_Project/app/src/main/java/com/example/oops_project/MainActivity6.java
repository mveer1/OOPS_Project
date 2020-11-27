package com.example.oops_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity6 extends AppCompatActivity {

    public static final int Add_Note_Request3 = 1;
    public static final int Edit_Note_Request3 = 2;
    private TodoViewModel noteViewModel3;
    private List<Todo> completeList3;
    RecyclerView recyclerView3;
    TodoAdapter adapter3;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;
    MaterialAlertDialogBuilder builder3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        final FloatingActionButton buttonAddTodo = findViewById(R.id.button_add_note3);
        buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity6.this, AddEditTodoActivity.class);
                startActivityForResult(intent, Add_Note_Request3);
            }
        });

        recyclerView3 = findViewById(R.id.recycler_view3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setHasFixedSize(true);

        adapter3 = new TodoAdapter();
        recyclerView3.setAdapter(adapter3);

        builder3 = new MaterialAlertDialogBuilder(
                new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        noteViewModel3 = ViewModelProviders.of(this).get(TodoViewModel.class);
        noteViewModel3.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> notes3) {
                adapter3.submitList(notes3);
                completeList3 = new ArrayList<>(notes3);
            }
        });


        new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                builder3.setBackground(getResources().getDrawable(R.drawable.alert_shape));
                builder3.setMessage("Do you want to delete this Todo ?")
                        .setTitle("Alert")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final int adapterPosition = viewHolder.getAdapterPosition();
                                final Todo mNote = adapter3.getTodosAt(adapterPosition);
                                final View viewPos = findViewById(R.id.myCoordinatorLayout);

                                Snackbar snackbar = Snackbar
                                        .make(recyclerView3, "Todo Deleted", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int mAdapterPosition = viewHolder.getAdapterPosition();
                                                noteViewModel3.insert(mNote);
                                            }
                                        });

                                snackbar.setActionTextColor(getResources().getColor(R.color.primaryLightColor));
                                View snackBarView = snackbar.getView();
                                int snackbarTextId = com.google.android.material.R.id.snackbar_text;
                                TextView textView = snackBarView.findViewById(snackbarTextId);
                                textView.setTextColor(getResources().getColor(R.color.primaryTextColor));
                                snackBarView.setBackground(getResources().getDrawable(R.drawable.snackbar_shape));
                                snackbar.show();

                                noteViewModel3.delete(adapter3.getTodosAt(viewHolder.getAdapterPosition()));
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter3.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder3.create();
                alert.show();

            }
        }).attachToRecyclerView(recyclerView3);

        adapter3.setOnItemClickListener(new TodoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Todo note) {
                Intent intent = new Intent(MainActivity6.this, AddEditTodoActivity.class);
                String title = note.getTitle();
                String description = note.getDescription();
                String priority = note.getPriority();
                String date = note.getDate();
                String time = note.getTime();

                int priorityNumber = 0;

                if (priority.equals("High")) {
                    priorityNumber = 3;
                } else if (priority.equals("Medium")) {
                    priorityNumber = 2;
                } else if (priority.equals("Low")) {
                    priorityNumber = 1;
                }

                int id = note.getId();
                intent.putExtra(AddEditTodoActivity.EXTRA_TITLE, title);
                intent.putExtra(AddEditTodoActivity.EXTRA_DESCRIPTION, description);
                intent.putExtra(AddEditTodoActivity.EXTRA_PRIORITY, priority);
                intent.putExtra(AddEditTodoActivity.EXTRA_PRIORITY_NUMBER, priorityNumber);
                intent.putExtra(AddEditTodoActivity.EXTRA_ID, id);
                intent.putExtra(AddEditTodoActivity.EXTRA_DATE, date);
                intent.putExtra(AddEditTodoActivity.EXTRA_TIME, time);
                startActivityForResult(intent, Edit_Note_Request3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_Note_Request3 && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            String priority = data.getStringExtra(AddEditTodoActivity.EXTRA_PRIORITY);
            String date = data.getStringExtra(AddEditTodoActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditTodoActivity.EXTRA_TIME);
            int priorityNumber = 0;

            if (priority.equals("High")) {
                priorityNumber = 3;
            } else if (priority.equals("Medium")) {
                priorityNumber = 2;
            } else if (priority.equals("Low")) {
                priorityNumber = 1;
            }

            Todo note = new Todo(title, description, priority, priorityNumber, date, time);
            noteViewModel3.insert(note);
            Toast.makeText(this, "Todo saved successfully!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Edit_Note_Request3 && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTodoActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Todo can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            String priority = data.getStringExtra(AddEditTodoActivity.EXTRA_PRIORITY);
            String date = data.getStringExtra(AddEditTodoActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditTodoActivity.EXTRA_TIME);
            int priorityNumber = 0;
            if (priority.equals("High")) {
                priorityNumber = 3;
            } else if (priority.equals("Medium")) {
                priorityNumber = 2;
            } else if (priority.equals("Low")) {
                priorityNumber = 1;
            }
            Todo note = new Todo(title, description, priority, priorityNumber, date, time);
            note.setId(id);
            noteViewModel3.update(note);

            Toast.makeText(this, "Todo updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Todo not saved!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        sharedPreferences3
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor3
                = sharedPreferences3.edit();

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();




        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getTodoFromDb(query);
                return false;
            }

            private void getTodoFromDb(String searchText) {
                searchText = "%" + searchText + "%";

                noteViewModel3.getSearchedTodos(searchText).observe(MainActivity6.this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> notes) {
                        if (notes == null) {
                            return;
                        }
//                        NoteAdapter adapter = new NoteAdapter();
                        adapter3.submitList(notes);
//                        completeList = notes;
//                        recyclerView.setAdapter(adapter);

                    }
                });

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getTodoFromDb(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes3:
                noteViewModel3.deleteAllTodos();
                Toast.makeText(this, "All Todo Deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}