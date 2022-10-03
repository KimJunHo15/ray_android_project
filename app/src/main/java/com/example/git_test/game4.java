package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class game4 extends AppCompatActivity {

    Button btn_game4_return;
    ConstraintLayout cl_game4;
    ImageView[] img_game4_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4);
        btn_game4_return = findViewById(R.id.btn_game4_return);
        cl_game4 = findViewById(R.id.cl_index);



    }
}