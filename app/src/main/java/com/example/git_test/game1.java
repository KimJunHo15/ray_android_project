package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class game1 extends AppCompatActivity {


    TextView tv_game1_best_score, tv_game1_show_best,tv_game1_now_show,tv_game1_now_score,tv_game_info;
    Button btn_game1_next;
    Button[] btn_game1_num;
    ImageView img_game1_info;

    int next_cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        btn_game1_next = findViewById(R.id.btn_game1_next);
        tv_game_info = findViewById(R.id.tv_game1_info);
        tv_game1_best_score = findViewById(R.id.tv_game1_best_score);
        tv_game1_show_best = findViewById(R.id.tv_game1_show_best);
        tv_game1_now_score = findViewById(R.id.tv_game1_now_score);
        tv_game1_now_show = findViewById(R.id.tv_game1_now_show);
        img_game1_info = findViewById(R.id.img_game1_info);

        btn_game1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(next_cnt==0){
                    img_game1_info.setImageResource(R.drawable.img3);
                    tv_game_info.setText("설명 1");
                    next_cnt+=1;
                }else if(next_cnt==1){
                    img_game1_info.setImageResource(R.drawable.img2);
                    tv_game_info.setText("설명2");
                    next_cnt+=2;
                }else if(next_cnt==3){
                    img_game1_info.setImageResource(R.drawable.img1);
                    tv_game_info.setText("설명3");
                    btn_game1_next.setText("게임시작");
                    img_game1_info.setVisibility(View.INVISIBLE);
                    tv_game_info.setVisibility(View.INVISIBLE);
                    next_cnt+=1;
                }
            }
        });
    }
}