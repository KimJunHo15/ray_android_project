package com.example.git_test;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class game2 extends AppCompatActivity {

    TextView tv_count_5, tv_game2_score,tv_game2_best;
    Button btn_test_return;
    ImageView[] game2_img_num;
    ConstraintLayout game2_cl;
    ProgressBar pro_game2;

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
    boolean isPlaying;
    int pro;

    // 최고 점수 용(볼리)
    String mem_id;
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request, request_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        tv_count_5 = findViewById(R.id.tv_count_5);
        tv_game2_score = findViewById(R.id.tv_game2_score);
        game2_cl = findViewById(R.id.game2_cl);
        btn_test_return = findViewById(R.id.btn_test_return);
        pro_game2 = findViewById(R.id.pro_game2);
        tv_game2_best = findViewById(R.id.tv_game2_best);
        tv_game2_score.setText("0");

        check_right = new ArrayList<>();
        check_right_id = new ArrayList<>();

        remain = 16;
        remain_count = 0;

        hideSystemUI();

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);
        String data = mem_id;
        Log.d("mem_id1", data);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url2 = "http://10.0.2.2:8000/mobile/gamesave";

        roof = true;
        isPlaying = true;
        pro_game2.setIndeterminate(false);
        pro_game2.setProgress(180);

        TimerThread timerThread = new TimerThread();
        timerThread.start();

        game2_img_num = new ImageView[16];

        getBestScore();


        for (int i = 0; i < game2_img_num.length; i++) {
            int img_id = getResources().getIdentifier("game2_img_" + (i + 1), "id", getPackageName());
            game2_img_num[i] = findViewById(img_id);
        }

        game2_set();

        if(isPlaying==false){
            int best = Integer.parseInt(tv_game2_score.getText().toString());
            tv_game2_best.setText(best);
        }


        btn_test_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game2.this,GameActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getBestScore() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);
        String data = mem_id;

        String url = "http://10.0.2.2:8000/mobile/gamescore";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    String best_score = json.getString("max2");

                    if (best_score.equals("null")) {
                        tv_game2_best.setText("0");
                    } else {
                        tv_game2_best.setText(best_score);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error + "");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mem_id", data);
                return params;
            }
        };
        requestQueue.add(request);
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
                        if (Integer.parseInt(tv_game2_score.getText().toString())%40==0) {
                            game2_set();
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
            game2_img_num[i].setVisibility(View.VISIBLE);
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
    Handler timeHandler = new Handler(){
        public void handleMessage(@NonNull Message msg) {
            int time = msg.arg1;
            pro = msg.arg2;
            if(pro<=180){
                int time_m = time/60;
                int time_s = time%60;
//                tv_game4_timer.setText("남은시간"+ time_m+" : "+time_s);
                pro_game2.setProgress(180-pro);
            }
            else{
//                tv_game4_timer.setText("게임종료");
                isPlaying=false;
                for(int i =0; i<16;i++){
                    game2_img_num[i].setClickable(false);
                }
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                mem_id = auto.getString("mem_id", mem_id);
                String data = mem_id;
                Log.d("mem_id2", data);
                String score = tv_game2_score.getText().toString();
                Log.d("now__________",tv_game2_score+"");
                String url = "http://10.0.2.2:8000/mobile/gamesave";

                request_score = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    String code = jsonObject.getString("code");
                                    if (code.equals(200)) {
//                                        Toast.makeText(game1.this, "저장완료", Toast.LENGTH_SHORT).show();
                                        Log.d("성공이다", "성공");

                                    } else {
                                        Log.d("error실패다", "실패");
                                    }
                                } catch (Exception e) {
//                                    Toast.makeText(game1.this, "에러발생", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(game1.this, error + "", Toast.LENGTH_SHORT).show();
                                Log.d("error에러입니다", error.toString());
                            }
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();

                        params.put("mem_id", data);
                        params.put("game_score2", score);
                        return params;
                    }
                };
                requestQueue.add(request_score);
            }
        }
    };

    class TimerThread extends  Thread{
        int time = 180;
        int timer = 0;
        @Override
        public void run() {
            while (isPlaying){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();

                message.arg1 = time;
                message.arg2 = timer;
                time--;
                timer+=1;
                timeHandler.sendMessage(message);
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