package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class MyActivity extends AppCompatActivity{

    ImageView img_return_m;
    TextView tv_name, tv_birth, tv_gender;
    Button btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        img_return_m = findViewById(R.id.img_return_m);
        tv_name = findViewById(R.id.tv_name);
        tv_birth = findViewById(R.id.tv_birth);
        tv_gender = findViewById(R.id.tv_gender);
        btn_logout = findViewById(R.id.btn_logout);

        img_return_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLoginEdit = auto.edit();

                autoLoginEdit.clear();
                autoLoginEdit.commit();

                Intent return_intent = new Intent(MyActivity.this, IndexActivity.class);
                startActivity(return_intent);

//                ((IndexActivity)IndexActivity.iContext).autologin_logout();
//                SharedPreferences SharedPreferences = getSharedPreferences("logout", MyActivity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = SharedPreferences.edit();
//
//                editor.clear();
//                editor.commit();
                finish();
            }
        });

    }
}