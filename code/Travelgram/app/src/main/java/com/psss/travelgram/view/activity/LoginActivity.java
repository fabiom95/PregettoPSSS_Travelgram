package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psss.travelgram.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView login = (TextView) findViewById(R.id.clicksignup);
        login.setOnClickListener(this);

        Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clicksignup:
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                break;
            default:
                break;
        }

    }
}