package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class game1 extends AppCompatActivity {


    TextView tv_game1_best_score, tv_game1_show_best, tv_game1_now_show, tv_game1_now_score, tv_game1_info;
    Button btn_game1_next;
    Button[] btn_game1_num;
    ImageView img_game1_info;

    int[] lotto = new int[9];
    Random rd = new Random();
    //    int op;
    int cnt;
    int answer;

    // 타이머
    ProgressBar pre_game1;
    private long timeCount = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    int next_cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        btn_game1_next = findViewById(R.id.btn_game1_next);
        tv_game1_info = findViewById(R.id.tv_game1_info);
        tv_game1_best_score = findViewById(R.id.tv_game1_best_score);
        tv_game1_show_best = findViewById(R.id.tv_game1_show_best);
        tv_game1_now_score = findViewById(R.id.tv_game1_now_score);
        tv_game1_now_show = findViewById(R.id.tv_game1_now_show);
        img_game1_info = findViewById(R.id.img_game1_info);
        pre_game1 = findViewById(R.id.pre_game1);

        btn_game1_num = new Button[9];

        for (int i = 0; i < btn_game1_num.length; i++) {
            int btn_id = getResources().getIdentifier("btn_game_num" + (i + 1), "id", getPackageName());
            btn_game1_num[i] = findViewById(btn_id);
        }

        btn_game1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (next_cnt == 0) {
                    img_game1_info.setImageResource(R.drawable.img3);
                    tv_game1_info.setText("설명 1");
                    next_cnt += 1;
                } else if (next_cnt == 1) {
                    img_game1_info.setImageResource(R.drawable.img2);
                    tv_game1_info.setText("설명2");
                    next_cnt += 1;
                } else if (next_cnt == 2) {
                    img_game1_info.setImageResource(R.drawable.img1);
                    tv_game1_info.setText("설명3");
                    btn_game1_next.setText("게임시작");
                    next_cnt += 1;
                } else if (next_cnt == 3) {
                    game1_info_hide();
                    game1_show();
                    start();
                    cnt = 0;
                    answer = 1;
                    makeRandom(cnt);
                    for (int i = 0; i < btn_game1_num.length; i++) {
                        final int pos = i;

                        btn_game1_num[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (lotto[pos] == answer) {
                                    btn_game1_num[pos].setVisibility(View.INVISIBLE);
                                    answer++;
                                    cnt++;
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void game1_info_hide() {
        img_game1_info.setVisibility(View.INVISIBLE);
        tv_game1_info.setVisibility(View.INVISIBLE);
        btn_game1_next.setVisibility(View.INVISIBLE);
    }

    private void game1_info_show() {
        img_game1_info.setVisibility(View.VISIBLE);
        tv_game1_info.setVisibility(View.VISIBLE);
        btn_game1_next.setVisibility(View.VISIBLE);
    }

    private void game1_show() {
        pre_game1.setVisibility(View.VISIBLE);
        tv_game1_now_score.setVisibility(View.VISIBLE);
        tv_game1_now_show.setVisibility(View.VISIBLE);
        tv_game1_show_best.setVisibility(View.VISIBLE);
        tv_game1_best_score.setVisibility(View.VISIBLE);
    }

    private void game1_hide() {
        pre_game1.setVisibility(View.INVISIBLE);
        tv_game1_now_score.setVisibility(View.INVISIBLE);
        tv_game1_now_show.setVisibility(View.INVISIBLE);
        tv_game1_show_best.setVisibility(View.INVISIBLE);
        tv_game1_best_score.setVisibility(View.INVISIBLE);
    }

    private void start() {
        if (timerStatus == TimerStatus.STOPPED) {
            timerStatus = TimerStatus.STARTED;
        }
    }

    private void makeRandom(int cnt) {
        Random rd = new Random();
        for (int i = 0; i < lotto.length; i++) {
            lotto[i] = rd.nextInt(9) + 1 + cnt;
            for (int j = 0; j < i; j++) {
                if (lotto[i] == lotto[j]) {
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < btn_game1_num.length; i++) {
            btn_game1_num[i].setText(lotto[i] + "");
            btn_game1_num[i].setVisibility(View.VISIBLE);
        }
//    private void startCountDownTimer(){
//         countDownTimer = new CountDownTimer(timeCount,1000){
//            @Override
//            public void onTick(long l) {
//            }
//            @Override
//            public void onFinish() {
//            }
//        }
//    }
    }
}
