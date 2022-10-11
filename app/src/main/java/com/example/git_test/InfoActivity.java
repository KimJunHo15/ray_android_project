package com.example.git_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class InfoActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    ImageView img_return_info;
    ConstraintLayout cl_info;
    boolean click_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.cl_info, MainFragment.newInstance()).commit();

        bnv = findViewById(R.id.bnv);
        //img_return_i = findViewById(R.id.img_return_i);
        cl_info = findViewById(R.id.cl_info);
        img_return_info = findViewById(R.id.img_return_info);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new MainFragment()).commit();

        cl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click_info ==true){
                    showSystemUI();
                    click_info = false;
                }
                else{
                    hideSystemUI();
                    click_info = true;
                }
            }
        });

        img_return_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new MainFragment()).commit();
                        break;
                    case R.id.item_food:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FoodFragment()).commit();
                        break;
                    case R.id.item_train:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new TrainingFragment()).commit();
                        break;
                    case R.id.item_column:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new ColumnFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cl_info, fragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}