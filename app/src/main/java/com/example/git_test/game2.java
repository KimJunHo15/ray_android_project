package com.example.git_test;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class game2 extends AppCompatActivity {

    TextView tv_count_5, tv_game2_score;
    Button btn_test, btn_test_return;
    ImageView[] game2_img_num;
    ConstraintLayout game2_cl;

    ArrayList<String> check_right;
    ArrayList<ImageView> check_right_id;
    int remain, remain_count;

    int count_turn;
    int count_list;
    int countDown;
    //    int[] id = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.imgfood1, R.drawable.imgfood2, R.drawable.imgfood3, R.drawable.imgfood4};
    boolean check_click = false;
    int score;
    boolean roof;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        tv_count_5 = findViewById(R.id.tv_count_5);
        tv_game2_score = findViewById(R.id.tv_game2_score);
        btn_test = findViewById(R.id.btn_test);
        game2_cl = findViewById(R.id.game2_cl);
        btn_test_return = findViewById(R.id.btn_test_return);

        check_right = new ArrayList<>();
        check_right_id = new ArrayList<>();

        remain = 16;
        remain_count = 0;

        hideSystemUI();

        roof = true;

        game2_img_num = new ImageView[16];

        timeThread Thread = new timeThread();

        for (int i = 0; i < game2_img_num.length; i++) {
            int img_id = getResources().getIdentifier("game2_img_" + (i + 1), "id", getPackageName());
            game2_img_num[i] = findViewById(img_id);
        }


            game2_set();


        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread.run();
            }
        });

        btn_test_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game2.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void makeTag() {
        roof=false;
        check_click = true;

        Random rd = new Random();
        int[] tag = new int[8];
        int[] rdn = new int[8];

        for (int i = 0; i < tag.length; i++) {
            tag[i] = rd.nextInt(8) + 1;
            for (int j = 0; j < i; j++) {
                if (tag[i] == tag[j]) {
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < tag.length; i++) {
            rdn[i] = rd.nextInt(8);

            for (int j = 0; j < i; j++) {
                if (rdn[i] == rdn[j]) {
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < rdn.length; i++) {
            int j = rdn[i] + 8;
            game2_img_num[i].setTag(tag[i]);
            game2_img_num[j].setTag(tag[i]);
        }

    }

    private void makeArraylist() {
        check_right = new ArrayList<>();
        check_right_id = new ArrayList<>();
        count_list = 0;
        count_turn = 0;
    }

    private void activeGame(int pos) {
        if (game2_img_num[pos].getTag() != null && count_turn <= 2) {
            if (game2_img_num[pos].getTag().toString().equals("1")) {
                game2_img_num[pos].setImageResource(R.drawable.imgfood1_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("2")) {
                game2_img_num[pos].setImageResource(R.drawable.imgfood2_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("3")) {
                game2_img_num[pos].setImageResource(R.drawable.imgfood3_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("4")) {
                game2_img_num[pos].setImageResource(R.drawable.imgfood4_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("5")) {
                game2_img_num[pos].setImageResource(R.drawable.imgfood5_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("6")) {
                game2_img_num[pos].setImageResource(R.drawable.imgfood6_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("7")) {
                game2_img_num[pos].setImageResource(R.drawable.img3_2);
                clickImage(pos);
            } else if (game2_img_num[pos].getTag().toString().equals("8")) {
                game2_img_num[pos].setImageResource(R.drawable.img4_2);
                clickImage(pos);
            }
        }
    }
    int num =0;

    private void check_img() {
        if (count_turn >= 2 && check_right.size() == 2) {
            score = Integer.parseInt(tv_game2_score.getText().toString());

            if (check_right.get(0) == check_right.get(1)) {
                for (int i = 0; i < check_right.size(); i++) {
                    check_right_id.get(i).setVisibility(View.INVISIBLE);
                    count_list = count_list + 1;
                    if (count_list == 2) {
                        makeArraylist();
                        num++;
                        tv_game2_score.setText((score + 5) + "");
                        remain = remain - 2;
                        if (remain == 0) {
                            num=0;
                            roof=true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < check_right.size(); i++) {
                    final int posi = i;
                    YoYo.with(Techniques.Shake).duration(300).playOn(check_right_id.get(posi));
                    check_right_id.get(posi).setImageResource(R.drawable.big_logo);
                    count_list = count_list + 1;
                    if (count_list == 2) {
                        check_right_id.get(0).setClickable(true);
                        check_right_id.get(1).setClickable(true);
                        makeArraylist();
                    }
                }
            }
        }
    }

    private void clickImage(int pos) {
        check_right.add(game2_img_num[pos].getTag().toString());
        check_right_id.add(game2_img_num[pos]);
        game2_img_num[pos].setClickable(false);
    }

    private void img_show(int i) {
        if (game2_img_num[i].getTag().toString().equals("1")) {
            game2_img_num[i].setImageResource(R.drawable.imgfood1_2);
        } else if (game2_img_num[i].getTag().toString().equals("2")) {
            game2_img_num[i].setImageResource(R.drawable.imgfood2_2);
        } else if (game2_img_num[i].getTag().toString().equals("3")) {
            game2_img_num[i].setImageResource(R.drawable.imgfood3_2);
        } else if (game2_img_num[i].getTag().toString().equals("4")) {
            game2_img_num[i].setImageResource(R.drawable.imgfood4_2);
        } else if (game2_img_num[i].getTag().toString().equals("5")) {
            game2_img_num[i].setImageResource(R.drawable.imgfood5_2);
        } else if (game2_img_num[i].getTag().toString().equals("6")) {
            game2_img_num[i].setImageResource(R.drawable.imgfood6_2);
        } else if (game2_img_num[i].getTag().toString().equals("7")) {
            game2_img_num[i].setImageResource(R.drawable.img3_2);
        } else if (game2_img_num[i].getTag().toString().equals("8")) {
            game2_img_num[i].setImageResource(R.drawable.img4_2);
        }
    }

    private void game2_set() {

        makeTag();
        for (int i = 0; i < game2_img_num.length; i++) {
            game2_img_num[i].setClickable(false);
            img_show(i);
            final int turn = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    game2_img_num[turn].setImageResource(R.drawable.big_logo);
                }
            }, 5000);
        }
        if (countDown == 0) {
            for (int j = 0; j < game2_img_num.length; j++) {
                final int pos = j;

                game2_img_num[pos].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count_turn += 1;
                        activeGame(pos);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                check_img();
                            }
                        }, 1000);
//                        if(Integer.parseInt(tv_game2_score.getText().toString())%40==0){
//                            activeGame(pos);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    check_img();
//                                }
//                            }, 1000);
//                        }
                    }
                });
            }
        }
    }

    private void game2_set_2() {

        if(Integer.parseInt(tv_count_5.getText().toString())%40==0){
            makeTag();
            for (int i = 0; i < game2_img_num.length; i++) {
                game2_img_num[i].setClickable(false);
                img_show(i);
                final int turn = i;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game2_img_num[turn].setImageResource(R.drawable.big_logo);
                    }
                }, 5000);
            }
            if (countDown == 0) {
                for (int j = 0; j < game2_img_num.length; j++) {
                    final int pos = j;

                    game2_img_num[pos].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            count_turn += 1;
                            activeGame(pos);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check_img();
                                }
                            }, 1000);
                        }
                    });
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tv_count_5.setText(msg.arg1 + "");
            countDown = msg.arg1;
            Log.d("i",msg.arg1+"");
            Log.d("count",countDown+"");
        }
    };

    class timeThread extends Thread {
        @Override
        public void run() {
            for (int i = 5; i >= 0; i--) {
                try {
                    Thread.sleep(1000); // (1000ms --> 1초)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("타이머", String.valueOf(i));
                Message message = new Message();
                message.arg1 = i;
                handler.sendMessage(message);
            }
        }
    }

    // UI 숨기기
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