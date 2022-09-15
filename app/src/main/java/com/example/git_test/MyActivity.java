package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity {

    ImageView img_return_m;
    TextView tv_name, tv_birth, tv_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        img_return_m = findViewById(R.id.img_return_m);
        tv_name = findViewById(R.id.tv_name);
        tv_birth = findViewById(R.id.tv_birth);
        tv_gender = findViewById(R.id.tv_gender);
    }
}