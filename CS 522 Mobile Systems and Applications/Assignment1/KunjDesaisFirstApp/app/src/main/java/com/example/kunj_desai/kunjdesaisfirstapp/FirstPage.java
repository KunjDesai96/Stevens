package com.example.kunj_desai.kunjdesaisfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FirstPage extends AppCompatActivity {
    TextView setName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("username");
        setName = findViewById(R.id.setName);
        setName.setText("Welcome"+ " "+ name );
    }
}
