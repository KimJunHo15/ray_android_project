package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    ImageView game_1, game_2,game_4, img_return_g;
    ConstraintLayout cl_game_main;

    boolean click_game_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game_1 = findViewById(R.id.game_1);
        game_2 = findViewById(R.id.game_2);
       // game_3 = findViewById(R.id.game_3);
        game_4 = findViewById(R.id.game_4);
        img_return_g = findViewById(R.id.img_return_g);
        cl_game_main = findViewById(R.id.cl_game_main);

        hideSystemUI();

        img_return_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cl_game_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click_game_main ==true){
                    showSystemUI();
                    click_game_main = false;
                }
                else{
                    hideSystemUI();
                    click_game_main = true;
                }
            }
        });

        game_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_game_1 = new Intent(GameActivity.this, game1.class);
                startActivity(intent_game_1);
            }
        });

        game_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_game_2 = new Intent(GameActivity.this, game2.class);
                startActivity(intent_game_2);
            }
        });

//        game_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent_game_3 = new Intent(GameActivity.this, game3.class);
//                startActivity(intent_game_3);
//            }
//        });

        game_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_game_4 = new Intent(GameActivity.this, game4.class);
                startActivity(intent_game_4);
            }
        });

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