package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        img_return_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
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

        game_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_game_3 = new Intent(GameActivity.this, game3.class);
                startActivity(intent_game_3);
            }
        });

        game_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_game_4 = new Intent(GameActivity.this, game4.class);
                startActivity(intent_game_4);
            }
        });

    }
}