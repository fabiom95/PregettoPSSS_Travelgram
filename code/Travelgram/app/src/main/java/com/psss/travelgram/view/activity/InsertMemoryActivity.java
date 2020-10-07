package com.psss.travelgram.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.psss.travelgram.R;
import com.psss.travelgram.view.fragment.DatePickerFragment;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

import java.util.Calendar;


public class InsertMemoryActivity extends AppCompatActivity implements OnClickListener {

    private AutoCompleteTextView country;
    private AutoCompleteTextView city;
    private EditText description;
    private int resultCode;
    private Uri uri;
    private ImageView memoryImage;
    private TextView date;
    private MenuItem shareBtn;
    private InsertMemoryViewModel insertMemoryViewModel;
    private FrameLayout progressBar;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_memory);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");
        username = intent.getStringExtra("username");


        // Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.new_memory));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Immagine
        memoryImage = findViewById(R.id.memoryImage);
        memoryImage.setOnClickListener(this);

        // data
        date = findViewById(R.id.date);
        date.setOnClickListener(this);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        date.setText(day + "/" + (month+1) + "/" + year);

        // progress Bar
        progressBar = findViewById(R.id.progressBar);

        // campi
        country = findViewById(R.id.select_country);
        city = findViewById(R.id.select_city);
        description = findViewById(R.id.description);

        String[] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);

        country.setAdapter(adapter);
        if (countryName != null)
            country.setText(countryName);


        // ViewModel
        insertMemoryViewModel = new InsertMemoryViewModel();

        // aspetta il via per l'azione successiva
        insertMemoryViewModel.getTaskResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try{
                    if(s.equals("success")){
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        setResult(1);
                        finish();
                        //TODO: far funzionare la finish
                    }
                    else {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                }catch(NullPointerException e) {e.printStackTrace();}
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insert_memory_menu, menu);
        shareBtn = menu.findItem(R.id.share);
        shareBtn.setEnabled(false);
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memoryImage:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
            case R.id.date:
                Log.e("PROVA","prova");
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                progressBar.setVisibility(View.VISIBLE);
                insertMemoryViewModel.insertMemory(resultCode, uri,
                        country.getText().toString(),
                        city.getText().toString(),
                        description.getText().toString(),
                        date.getText().toString(),
                        username
                );
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            shareBtn.setEnabled(true);
            this.uri = data.getData();
            // immagine
            Glide.with(getApplicationContext())
                    .load(this.uri)
                    .apply(new RequestOptions().override(700))      // immagine a dimensione ridotta
                    .thumbnail(0.2f)                                // thumbnail per il caricamento
                    .into(memoryImage);
        }

        this.resultCode = resultCode;

    }
}