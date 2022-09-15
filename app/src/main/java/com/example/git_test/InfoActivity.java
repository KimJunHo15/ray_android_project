package com.example.git_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class InfoActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    ImageView img_return_i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        bnv = findViewById(R.id.bnv);
        img_return_i = findViewById(R.id.img_return_i);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new MainFragment()).commit();

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
                return false;
            }
        });
    }
}