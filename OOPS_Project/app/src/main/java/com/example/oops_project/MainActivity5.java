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

public class MainActivity5 extends AppCompatActivity {

    public static final int Add_Note_Request2 = 1;
    public static final int Edit_Note_Request2 = 2;
    private StockViewModel noteViewModel2;
    private List<Stock> completeList2;
    RecyclerView recyclerView2;
    StockAdapter adapter2;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    MaterialAlertDialogBuilder builder2;


    @Override
    protected void onCreate(Bundle savedInstanceState2) {
        super.onCreate(savedInstanceState2);
        setContentView(R.layout.activity_main5);

        final FloatingActionButton buttonAddNote2 = findViewById(R.id.button_add_note2);
        buttonAddNote2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               in next line have to change to AddEditTodoActivity
                Intent intent2 = new Intent(MainActivity5.this, AddEditStockActivity.class);
                startActivityForResult(intent2, Add_Note_Request2);
            }
        });

        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setHasFixedSize(true);

        adapter2 = new StockAdapter();
        recyclerView2.setAdapter(adapter2);

        builder2 = new MaterialAlertDialogBuilder(
                new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        noteViewModel2 = ViewModelProviders.of(this).get(StockViewModel.class);
        noteViewModel2.getAllStock().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(@Nullable List<Stock> notes2) {
                adapter2.submitList(notes2);
                completeList2 = new ArrayList<>(notes2);
            }
        });


        new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                builder2.setBackground(getResources().getDrawable(R.drawable.alert_shape));
                builder2.setMessage("Do you want to delete this Stock ?")
                        .setTitle("Alert")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int id2) {
                                final int adapterPosition2 = viewHolder.getAdapterPosition();
                                final Stock mNote2 = adapter2.getStockAt(adapterPosition2);
                                final View viewPos2 = findViewById(R.id.myCoordinatorLayout2);

                                Snackbar snackbar2 = Snackbar
                                        .make(recyclerView2, "Stock Deleted", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view2) {
                                                int mAdapterPosition2 = viewHolder.getAdapterPosition();
                                                noteViewModel2.insert(mNote2);
                                            }
                                        });

                                snackbar2.setActionTextColor(getResources().getColor(R.color.primaryLightColor));
                                View snackBarView = snackbar2.getView();
                                int snackbarTextId2 = com.google.android.material.R.id.snackbar_text;
                                TextView textView51 = snackBarView.findViewById(snackbarTextId2);
                                textView51.setTextColor(getResources().getColor(R.color.primaryTextColor));
                                snackBarView.setBackground(getResources().getDrawable(R.drawable.snackbar_shape));
                                snackbar2.show();

                                noteViewModel2.delete(adapter2.getStockAt(viewHolder.getAdapterPosition()));
                                dialog2.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int id2) {
                                adapter2.notifyDataSetChanged();
                                dialog2.cancel();
                            }
                        });
                AlertDialog alert2 = builder2.create();
                alert2.show();

            }
        }).attachToRecyclerView(recyclerView2);

        adapter2.setOnItemClickListener(new StockAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Stock note2) {
                Intent intent2 = new Intent(MainActivity5.this, AddEditNoteActivity.class);
                String title = note2.getTitle();
                String description2 = note2.getDescription();
                String priority2 = note2.getPriority();
                String date2 = note2.getDate();
                String time2 = note2.getTime();

                int priorityNumber2 = 0;

                if (priority2.equals("High")) {
                    priorityNumber2 = 3;
                } else if (priority2.equals("Medium")) {
                    priorityNumber2 = 2;
                } else if (priority2.equals("Low")) {
                    priorityNumber2 = 1;
                }

                int id2 = note2.getId();
                intent2.putExtra(AddEditStockActivity.EXTRA_TITLE, title);
                intent2.putExtra(AddEditStockActivity.EXTRA_DESCRIPTION, description2);
                intent2.putExtra(AddEditStockActivity.EXTRA_PRIORITY, priority2);
                intent2.putExtra(AddEditStockActivity.EXTRA_PRIORITY_NUMBER, priorityNumber2);
                intent2.putExtra(AddEditStockActivity.EXTRA_ID, id2);
                intent2.putExtra(AddEditStockActivity.EXTRA_DATE, date2);
                intent2.putExtra(AddEditStockActivity.EXTRA_TIME, time2);
                startActivityForResult(intent2, Edit_Note_Request2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode2, int resultCode2, @Nullable Intent data2) {
        super.onActivityResult(requestCode2, resultCode2, data2);
        if (requestCode2 == Add_Note_Request2 && resultCode2 == RESULT_OK) {
            String title2 = data2.getStringExtra(AddEditStockActivity.EXTRA_TITLE);
            String description2 = data2.getStringExtra(AddEditStockActivity.EXTRA_DESCRIPTION);
            String priority2 = data2.getStringExtra(AddEditStockActivity.EXTRA_PRIORITY);
            String date2 = data2.getStringExtra(AddEditStockActivity.EXTRA_DATE);
            String time2 = data2.getStringExtra(AddEditStockActivity.EXTRA_TIME);
            int priorityNumber2 = 0;

            if (priority2.equals("High")) {
                priorityNumber2 = 3;
            } else if (priority2.equals("Medium")) {
                priorityNumber2 = 2;
            } else if (priority2.equals("Low")) {
                priorityNumber2 = 1;
            }

            Stock note2 = new Stock(title2, description2, priority2, priorityNumber2, date2, time2);
            noteViewModel2.insert(note2);
            Toast.makeText(this, "Stock saved successfully!", Toast.LENGTH_SHORT).show();
        } else if (requestCode2 == Edit_Note_Request2 && resultCode2 == RESULT_OK) {
            int id2 = data2.getIntExtra(AddEditStockActivity.EXTRA_ID, -1);

            if (id2 == -1) {
                Toast.makeText(this, "Stock can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title2 = data2.getStringExtra(AddEditStockActivity.EXTRA_TITLE);
            String description2 = data2.getStringExtra(AddEditStockActivity.EXTRA_DESCRIPTION);
            String priority2 = data2.getStringExtra(AddEditStockActivity.EXTRA_PRIORITY);
            String date2 = data2.getStringExtra(AddEditStockActivity.EXTRA_DATE);
            String time2 = data2.getStringExtra(AddEditStockActivity.EXTRA_TIME);
            int priorityNumber2 = 0;
            if (priority2.equals("High")) {
                priorityNumber2 = 3;
            } else if (priority2.equals("Medium")) {
                priorityNumber2 = 2;
            } else if (priority2.equals("Low")) {
                priorityNumber2 = 1;
            }
            Stock note2 = new Stock(title2, description2, priority2, priorityNumber2, date2, time2);
            note2.setId(id2);

            noteViewModel2.update(note2);

            Toast.makeText(this, "Stock updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Stock not entered!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        MenuInflater menuInflater2 = getMenuInflater();
        menuInflater2.inflate(R.menu.main_menu2, menu2);
        sharedPreferences2
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor2
                = sharedPreferences2.edit();

        MenuItem searchItem2 = menu2.findItem(R.id.action_search2);
        SearchView searchView2 = (SearchView) searchItem2.getActionView();




        searchView2.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query2) {
                getStocksFromDb(query2);
                return false;
            }

            private void getStocksFromDb(String searchText2) {
                searchText2 = "%" + searchText2 + "%";

                noteViewModel2.getSearchedStocks(searchText2).observe(MainActivity5.this, new Observer<List<Stock>>() {
                    @Override
                    public void onChanged(List<Stock> notes2) {
                        if (notes2 == null) {
                            return;
                        }
//                        NoteAdapter adapter = new NoteAdapter();
                        adapter2.submitList(notes2);
//                        completeList = notes;
//                        recyclerView.setAdapter(adapter);

                    }
                });

            }

            @Override
            public boolean onQueryTextChange(String newText2) {
                getStocksFromDb(newText2);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item2) {
        switch (item2.getItemId()) {
            case R.id.delete_all_notes2:
                noteViewModel2.deleteAllStocks();
                Toast.makeText(this, "All Stocks Deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item2);
        }
    }
}