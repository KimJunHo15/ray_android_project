package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img_exam,img_game,img_info,img_my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // git_test test
        // test2
        img_exam = findViewById(R.id.img_exam);
        img_game = findViewById(R.id.img_game);
        img_info = findViewById(R.id.img_info);
        img_my = findViewById(R.id.img_my);


    }
}