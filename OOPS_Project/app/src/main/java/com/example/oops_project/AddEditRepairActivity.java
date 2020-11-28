package com.example.oops_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEditRepairActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.achitectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.achitectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.achitectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.achitectureexample.EXTRA_PRIORITY";
    public static final String EXTRA_PRIORITY_NUMBER = "com.example.achitectureexample.EXTRA_PRIORITY_NUMBER";
    public static final String EXTRA_DATE = "com.example.achitectureexample.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.example.achitectureexample.EXTRA_TIME";

    private TextView tvDate, tvTime;
    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDescription;
    private Spinner spinnerPriority;
    //private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair);

        Calendar calendar = Calendar.getInstance();
        //currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String date = dateFormat.format(calendar.getTime());
        String ntime = timeFormat.format(calendar.getTime());
        String time = ntime.replace("am", "AM").replace("pm", "PM");

        editTextTitle = findViewById(R.id.edit_text_title4);
        editTextDescription = findViewById(R.id.edit_text_Description4);
        spinnerPriority = findViewById(R.id.spinnerPriority4);
        tvDate = findViewById(R.id.tv_date4);
        tvTime = findViewById(R.id.tv_time4);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Repair");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            tvDate.setText(intent.getStringExtra(EXTRA_DATE));
            tvTime.setText(intent.getStringExtra(EXTRA_TIME));
            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.priorityList,R.layout.style_spinner);
            String[] array2 = {"Appliance Repair","Plumber", "Electrician","Carpenter"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.style_spinner, array2);
            spinnerPriority.setAdapter(adapter);
            //spinnerPriority.setSelection(intent.getIntExtra(EXTRA_PRIORITY_NUMBER,1));
        } else {
            setTitle("Add Repair");
            tvDate.setText(date);
            tvTime.setText(time);
            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.priorityList,R.layout.style_spinner);
            String[] array2 = {"Appliance Repair","Plumber", "Electrician","Carpenter"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.style_spinner, array2);
            spinnerPriority.setAdapter(adapter);
            //spinnerPriority.setSelection(intent.getIntExtra(EXTRA_PRIORITY_NUMBER,1));
        }
    }

    private void saveRepair() {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String priority = spinnerPriority.getSelectedItem().toString();
        String date = tvDate.getText().toString();
        String time = tvTime.getText().toString();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_PRIORITY_NUMBER, spinnerPriority.getSelectedItem().toString());
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_TIME, time);

        int priorityNumber = 0;

        if (priority.equals("Appliance Repair")) {
            priorityNumber = 4;
        } else if (priority.equals("Plumber")) {
            priorityNumber = 3;
        } else if (priority.equals("Electrician")) {
            priorityNumber = 2;
        } else if (priority.equals("Carpenter")) {
            priorityNumber = 2;
        }


        data.putExtra(EXTRA_PRIORITY_NUMBER, priorityNumber);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_repair_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_repair:
                saveRepair();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void share_note(View view) {

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_Description);

        String titleText = editTextTitle.getText().toString();
        String descriptionText = editTextDescription.getText().toString();

        String data = "Title: " + titleText + "\n" + "Description: " + descriptionText;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}