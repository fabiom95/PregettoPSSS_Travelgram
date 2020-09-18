package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.psss.travelgram.R;


public class LoginActivity extends AppCompatActivity implements OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView login = (TextView) findViewById(R.id.clickSignup);
        login.setOnClickListener(this);

        Button button = (Button) findViewById(R.id.loginBtn);
        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clickSignup:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                break;
            case R.id.loginBtn:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            default:
                break;
        }

    }
}