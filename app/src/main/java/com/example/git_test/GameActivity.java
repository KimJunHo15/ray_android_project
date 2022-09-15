package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    ImageView game_1, game_2, game_3, game_4, img_return_g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game_1 = findViewById(R.id.game_1);
        game_2 = findViewById(R.id.game_2);
        game_3 = findViewById(R.id.game_3);
        game_4 = findViewById(R.id.game_4);
        img_return_g = findViewById(R.id.img_return_g);

    }
}