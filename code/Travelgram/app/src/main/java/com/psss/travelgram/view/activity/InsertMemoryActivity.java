package com.psss.travelgram.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;


public class InsertMemoryActivity extends AppCompatActivity implements OnClickListener {

    private AutoCompleteTextView country;
    private EditText description;
    private int resultCode;
    private Uri uri;
    private ImageView memoryImage;

    private InsertMemoryViewModel insertMemoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_memory);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");

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

        // campi
        String[] countries = getResources().getStringArray(R.array.countries);
        country = findViewById(R.id.select_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        country.setAdapter(adapter);
        if (countryName != null)
            country.setText(countryName);
        description = findViewById(R.id.description);


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
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                //TODO: if data!=null
                insertMemoryViewModel.insertMemory(resultCode, uri, country, description);
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
            this.uri = data.getData();
            memoryImage.setImageURI(this.uri);
        }

        this.resultCode = resultCode;

    }
}