package com.example.oops_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.oops_project.Repair.REQUEST_IMAGE_CAPTURE;

public class AddEditRepairActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.achitectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.achitectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.achitectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.achitectureexample.EXTRA_PRIORITY";
    public static final String EXTRA_PRIORITY_NUMBER = "com.example.achitectureexample.EXTRA_PRIORITY_NUMBER";
    public static final String EXTRA_DATE = "com.example.achitectureexample.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.example.achitectureexample.EXTRA_TIME";
    private static final int PICK_IMAGE = 1;

    private TextView tvDate, tvTime;
    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDescription;
    private Spinner spinnerPriority;
    //private String currentDate;

    private ImageView imageView;
    String currentPhotoPath;


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

        imageView =  findViewById(R.id.imageView2);

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
            priorityNumber = 1;
        }


        data.putExtra(EXTRA_PRIORITY_NUMBER, priorityNumber);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    public void callRepair()
    {

        String priority = spinnerPriority.getSelectedItem().toString();
        int priorityNumber = 0;

        if (priority.equals("Appliance Repair")) {
            priorityNumber = 4;
        } else if (priority.equals("Plumber")) {
            priorityNumber = 3;
        } else if (priority.equals("Electrician")) {
            priorityNumber = 2;
        } else if (priority.equals("Carpenter")) {
            priorityNumber = 1;
        }

        Uri u= Uri.parse("tel:9981222222");
        if(priorityNumber == 4) {
            Toast.makeText(this, "Calling Service Engineer", Toast.LENGTH_LONG)
                    .show();
            u = Uri.parse("tel:9981224444");
        }
        if(priorityNumber == 3) {
            Toast.makeText(this, "Calling Plumber", Toast.LENGTH_LONG)
                    .show();
            u = Uri.parse("tel:9981225555");
        }
        if(priorityNumber == 2) {
            Toast.makeText(this, "Calling Electrician", Toast.LENGTH_LONG)
                    .show();
            u = Uri.parse("tel:9981226666");
        }
        if(priorityNumber == 1) {
            Toast.makeText(this, "Calling Carpenter", Toast.LENGTH_LONG)
                    .show();
            u = Uri.parse("tel:9981227777");
        }

        // Create the intent and set the data for the
        // intent as the phone number.
        Intent i = new Intent(Intent.ACTION_DIAL, u);

        try
        {

            startActivity(i);
        }
        catch (SecurityException s)
        {

            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                    .show();
        }
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
            case R.id.share_repair:
                shareRepair();
                return true;
            case R.id.call_repair:
                callRepair();
                return true;
            case R.id.click_photo:
                takeRepairPhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareRepair() {

        editTextTitle = findViewById(R.id.edit_text_title4);
        editTextDescription = findViewById(R.id.edit_text_Description4);

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

    public void takeRepairPhoto(){
   }


    Uri imageUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


}
