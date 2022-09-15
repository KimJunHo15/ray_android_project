package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ExamActivity extends AppCompatActivity {

    ImageView img_return_e, img_camera, img_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        img_return_e = findViewById(R.id.img_return_e);
        img_camera = findViewById(R.id.img_camera);
        img_upload = findViewById(R.id.img_upload);
    }
}