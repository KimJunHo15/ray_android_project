package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        img_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExamActivity.class);
                startActivity(intent);
            }
        });

        img_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        img_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });


    }
}