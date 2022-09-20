package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Exam_upload extends AppCompatActivity {

    ImageView img_upload_img;
    Button btn_upload, btn_upload_send, btn_re_select;
    TextView tv_filename;

    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_upload);

        img_upload_img = findViewById(R.id.img_upload_img);
        btn_upload = findViewById(R.id.btn_upload);
        btn_upload_send = findViewById(R.id.btn_upload_send);
        btn_re_select = findViewById(R.id.btn_re_select);
        tv_filename = findViewById(R.id.tv_filename);
        img_upload_img.setImageResource(R.drawable.big_logo);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btn_re_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_seal();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri uri = data.getData();
                    Glide.with(getApplicationContext()).load(uri).into(img_upload_img);
                    Log.d("uri",uri+"");
                    tv_filename.setText(uri+"");
                    upload_show();
                } catch (Exception e) {

                }
            }
        } else if (resultCode == REQUEST_CODE) {
            Toast.makeText(this, "사진을 다시 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void upload_show() {
        btn_upload.setVisibility(View.INVISIBLE);
        btn_upload_send.setVisibility(View.VISIBLE);
        btn_re_select.setVisibility(View.VISIBLE);
        tv_filename.setVisibility(View.VISIBLE);
    }

    public void upload_seal() {
        img_upload_img.setImageResource(R.drawable.big_logo);
        btn_upload.setVisibility(View.VISIBLE);
        btn_upload_send.setVisibility(View.INVISIBLE);
        btn_re_select.setVisibility(View.INVISIBLE);
        tv_filename.setVisibility(View.INVISIBLE);
    }
}

