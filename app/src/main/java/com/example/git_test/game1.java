package com.example.git_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class game1 extends AppCompatActivity {


    TextView tv_game1_best_score, tv_game1_show_best, tv_game1_now_show, tv_game1_now_score, tv_game1_info;
    Button btn_game1_next, btn_game1_return;
    Button[] btn_game1_num;
    ImageView img_game1_info;

    int[] lotto = new int[9];
    Random rd = new Random();
    //    int op;
    int cnt;
    int answer;
    boolean isPlaying = true;
    boolean playCheck = true;
    int pro;

    // 타이머
    ProgressBar pro_game1;
    int next_cnt = 0;

    // 볼리용
    String mem_id;
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request, request_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        btn_game1_next = findViewById(R.id.btn_game1_next);
        btn_game1_return = findViewById(R.id.btn_game1_return);
        tv_game1_info = findViewById(R.id.tv_game1_info);
        tv_game1_best_score = findViewById(R.id.tv_game1_best_score);
        tv_game1_show_best = findViewById(R.id.tv_game1_show_best);
        tv_game1_now_score = findViewById(R.id.tv_game1_now_score);
        tv_game1_now_show = findViewById(R.id.tv_game1_now_show);
        img_game1_info = findViewById(R.id.img_game1_info);
        pro_game1 = findViewById(R.id.pro_game1);

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);
        String data = mem_id;
        Log.d("mem_id1", data);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        String score = tv_game1_now_score.toString();
        String url2 = "http://10.0.2.2:8000/mobile/gamesave";
//        String url2 = "http://172.30.1.28/mobile/gamesave";


        btn_game1_num = new Button[9];

        btn_game1_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game1.this, GameActivity.class);
                startActivity(intent);
            }
        });


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
                    for (int i = 0; i < btn_game1_num.length; i++) {
                        Log.d("아이디값 확인", btn_game1_num[i] + "");
                    }
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
                    getBestScore();
                    game1_info_hide();
                    game1_show();
                    pro_game1.setProgress(180);
                    TimerThread timerThread = new TimerThread();
                    timerThread.start();
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
                                    tv_game1_now_score.setText(cnt + "");
                                    if (cnt % 9 == 0) {
                                        makeRandom(cnt);
                                    }
                                } else {
                                    YoYo.with(Techniques.Shake).duration(500).playOn(btn_game1_num[pos]);
                                }
                            }
                        });
                    }
                }
                ;
            }
            ;
        });
    }


    // 버튼 보여주기 & 숨기기
    private void getBestScore() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);
        String data = mem_id;

        String url = "http://10.0.2.2:8000/mobile/gamescore";
//        String url = "http://172.30.1.28:8000/mobile/gamescore";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    String best_score = json.getString("max1");

                    if (best_score.equals("null")) {
                        tv_game1_best_score.setText("0");
                    } else {
                        tv_game1_best_score.setText(best_score);
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

    private void setBestScore() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);
        String data = mem_id;
        Log.d("mem_id2", data);
        String score = tv_game1_best_score.toString();
        String url = "http://10.0.2.2:8000/mobile/gamesave";
//        String url = "http://172.30.1.28:8000/mobile/gamesave";

        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            String code = jsonObject.getString("code");
                            if (code.equals(200)) {
                                Toast.makeText(game1.this, "저장완료", Toast.LENGTH_SHORT).show();
                                Log.d("성공이다", "성공");

                            } else {
                                Log.d("error실패다", "실패");
                            }
                        } catch (Exception e) {
                            Toast.makeText(game1.this, "에러발생", Toast.LENGTH_SHORT).show();
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
                params.put("game_score1", score);
                return params;
            }
        };
        requestQueue.add(request);
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
        btn_game1_return.setVisibility(View.VISIBLE);
        pro_game1.setVisibility(View.VISIBLE);
        tv_game1_now_score.setVisibility(View.VISIBLE);
        tv_game1_now_show.setVisibility(View.VISIBLE);
        tv_game1_show_best.setVisibility(View.VISIBLE);
        tv_game1_best_score.setVisibility(View.VISIBLE);
    }

    private void game1_hide() {
        pro_game1.setVisibility(View.INVISIBLE);
        tv_game1_now_score.setVisibility(View.INVISIBLE);
        tv_game1_now_show.setVisibility(View.INVISIBLE);
        tv_game1_show_best.setVisibility(View.INVISIBLE);
        tv_game1_best_score.setVisibility(View.INVISIBLE);
    }


    // 버튼에 보여지는 숫자 적용 및 변경
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
    }


    // 타이머 핸들러
    Handler timeHandler = new Handler() {
        public void handleMessage(@NonNull Message msg) {
            int time = msg.arg1;
            pro = msg.arg2;
            if (pro <= 180) {
//                int time_m = time / 60;
//                int time_s = time % 60;
////                tv_game4_timer.setText("남은시간"+ time_m+" : "+time_s);
                pro_game1.setProgress(180 - pro);
                Log.d("pro_game_____", pro_game1.getProgress() + "");
            } else {
//                tv_game4_timer.setText("게임종료");
                isPlaying = false;
                playCheck = false;
                for(int i =0; i<9;i++){
                    btn_game1_num[i].setClickable(false);
                }
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                mem_id = auto.getString("mem_id", mem_id);
                String data = mem_id;
                Log.d("mem_id2", data);
                String score = tv_game1_now_score.getText().toString();
                Log.d("now__________",tv_game1_now_score+"");
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
                                        Toast.makeText(game1.this, "저장완료", Toast.LENGTH_SHORT).show();
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
                        params.put("game_score1", score);
                        return params;
                    }
                };
                requestQueue.add(request_score);
            }
        }
    };

    class TimerThread extends Thread {
        int time = 10;
        int timer = 0;

        @Override
        public void run() {
            while (isPlaying) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();

                message.arg1 = time;
                message.arg2 = timer;
                time--;
                timer += 1;
                timeHandler.sendMessage(message);
            }
        }
    }
}
