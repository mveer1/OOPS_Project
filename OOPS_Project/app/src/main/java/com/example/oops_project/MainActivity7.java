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

public class MainActivity7 extends AppCompatActivity {

    public static final int Add_Note_Request4 = 1;
    public static final int Edit_Note_Request4 = 2;
    private RepairViewModel noteViewModel4;
    private List<Repair> completeList4;
    RecyclerView recyclerView4;
    RepairAdapter adapter4;
    SharedPreferences sharedPreferences4;
    SharedPreferences.Editor editor4;
    MaterialAlertDialogBuilder builder4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        final FloatingActionButton buttonAddRepair = findViewById(R.id.button_add_note4);
        buttonAddRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity7.this, AddEditRepairActivity.class);
                startActivityForResult(intent, Add_Note_Request4);
            }
        });

        recyclerView4 = findViewById(R.id.recycler_view4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setHasFixedSize(true);

        adapter4 = new RepairAdapter();
        recyclerView4.setAdapter(adapter4);

        builder4 = new MaterialAlertDialogBuilder(
                new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        noteViewModel4 = ViewModelProviders.of(this).get(RepairViewModel.class);
        noteViewModel4.getAllRepairs().observe(this, new Observer<List<Repair>>() {
            @Override
            public void onChanged(@Nullable List<Repair> notes4) {
                adapter4.submitList(notes4);
                completeList4 = new ArrayList<>(notes4);
            }
        });


        new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                builder4.setBackground(getResources().getDrawable(R.drawable.alert_shape));
                builder4.setMessage("Do you want to delete this Repair ?")
                        .setTitle("Alert")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final int adapterPosition = viewHolder.getAdapterPosition();
                                final Repair mNote = adapter4.getRepairAt(adapterPosition);
                                final View viewPos = findViewById(R.id.myCoordinatorLayout);

                                Snackbar snackbar = Snackbar
                                        .make(recyclerView4, "Repair Deleted", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int mAdapterPosition = viewHolder.getAdapterPosition();
                                                noteViewModel4.insert(mNote);
                                            }
                                        });

                                snackbar.setActionTextColor(getResources().getColor(R.color.primaryLightColor));
                                View snackBarView = snackbar.getView();
                                int snackbarTextId = com.google.android.material.R.id.snackbar_text;
                                TextView textView = snackBarView.findViewById(snackbarTextId);
                                textView.setTextColor(getResources().getColor(R.color.primaryTextColor));
                                snackBarView.setBackground(getResources().getDrawable(R.drawable.snackbar_shape));
                                snackbar.show();

                                noteViewModel4.delete(adapter4.getRepairAt(viewHolder.getAdapterPosition()));
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter4.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder4.create();
                alert.show();

            }
        }).attachToRecyclerView(recyclerView4);

        adapter4.setOnItemClickListener(new RepairAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Repair note) {
                Intent intent = new Intent(MainActivity7.this, AddEditRepairActivity.class);
                String title = note.getTitle();
                String description = note.getDescription();
                String priority = note.getPriority();
                String date = note.getDate();
                String time = note.getTime();

                int priorityNumber = 0;

                if (priority.equals("Appliance Repair")) {
                    priorityNumber = 4;
                } else if (priority.equals("Plumber")) {
                    priorityNumber = 3;
                } else if (priority.equals("Electrician")) {
                    priorityNumber = 2;
                }else if (priority.equals("Carpenter")) {
                    priorityNumber = 1;
                }

                int id = note.getId();
                intent.putExtra(AddEditRepairActivity.EXTRA_TITLE, title);
                intent.putExtra(AddEditRepairActivity.EXTRA_DESCRIPTION, description);
                intent.putExtra(AddEditRepairActivity.EXTRA_PRIORITY, priority);
                intent.putExtra(AddEditRepairActivity.EXTRA_PRIORITY_NUMBER, priorityNumber);
                intent.putExtra(AddEditRepairActivity.EXTRA_ID, id);
                intent.putExtra(AddEditRepairActivity.EXTRA_DATE, date);
                intent.putExtra(AddEditRepairActivity.EXTRA_TIME, time);
                startActivityForResult(intent, Edit_Note_Request4);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_Note_Request4 && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditRepairActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditRepairActivity.EXTRA_DESCRIPTION);
            String priority = data.getStringExtra(AddEditRepairActivity.EXTRA_PRIORITY);
            String date = data.getStringExtra(AddEditRepairActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditRepairActivity.EXTRA_TIME);
            int priorityNumber = 0;

            if (priority.equals("Appliance Repair")) {
                    priorityNumber = 4;
                } else if (priority.equals("Plumber")) {
                    priorityNumber = 3;
                } else if (priority.equals("Electrician")) {
                    priorityNumber = 2;
                }else if (priority.equals("Carpenter")) {
                priorityNumber = 1;
            }

            Repair note = new Repair(title, description, priority, priorityNumber, date, time);
            noteViewModel4.insert(note);
            Toast.makeText(this, "Repair saved successfully!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Edit_Note_Request4 && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTodoActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Repair can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditRepairActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditRepairActivity.EXTRA_DESCRIPTION);
            String priority = data.getStringExtra(AddEditRepairActivity.EXTRA_PRIORITY);
            String date = data.getStringExtra(AddEditRepairActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditRepairActivity.EXTRA_TIME);
            int priorityNumber = 0;
            if (priority.equals("Appliance Repair")) {
                priorityNumber = 4;
            } else if (priority.equals("Plumber")) {
                priorityNumber = 3;
            } else if (priority.equals("Electrician")) {
                priorityNumber = 2;
            }else if (priority.equals("Carpenter")) {
                priorityNumber = 1;
            }
            Repair note = new Repair(title, description, priority, priorityNumber, date, time);
            note.setId(id);
            noteViewModel4.update(note);

            Toast.makeText(this, "Repair updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Repair not saved!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu4, menu);
        sharedPreferences4
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor4
                = sharedPreferences4.edit();

        MenuItem searchItem = menu.findItem(R.id.action_search4);
        SearchView searchView = (SearchView) searchItem.getActionView();




        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getRepairFromDb(query);
                return false;
            }

            private void getRepairFromDb(String searchText) {
                searchText = "%" + searchText + "%";

                noteViewModel4.getSearchedRepairs(searchText).observe(MainActivity7.this, new Observer<List<Repair>>() {
                    @Override
                    public void onChanged(List<Repair> notes) {
                        if (notes == null) {
                            return;
                        }
//                        NoteAdapter adapter = new NoteAdapter();
                        adapter4.submitList(notes);
//                        completeList = notes;
//                        recyclerView.setAdapter(adapter);

                    }
                });

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getRepairFromDb(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes3:
                noteViewModel4.deleteAllRepairs();
                Toast.makeText(this, "All Repair Deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void goto_todo76(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity6.class));
    }
    public void goto_stock75(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity5.class));
    }
    public void goto_note74(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity4.class));
    }

    public void goto_abtus(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),MainActivity8.class));
    }
}