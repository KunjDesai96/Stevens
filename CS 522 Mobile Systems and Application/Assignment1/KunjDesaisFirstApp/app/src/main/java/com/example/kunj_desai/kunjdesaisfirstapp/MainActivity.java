package com.example.kunj_desai.kunjdesaisfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEt;
    Button nextBtn;
    String nameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEt = (EditText) findViewById(R.id.nameEt);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEt.getText().toString();
                Intent intent = new Intent(MainActivity.this,FirstPage.class);
                intent.putExtra("username",nameString);
                startActivity(intent);
            }
        });
    }
}
