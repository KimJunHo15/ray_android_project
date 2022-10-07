package com.example.git_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class game4 extends AppCompatActivity {

    Random rd = new Random();


    Button btn_game4_return;
    ConstraintLayout cl_game4;
    ImageView[] img_game4_num;
    ImageView[] img_correct_num;
    TextView tv_game4_score,tv_game4_indicate,tv_game4_best;
    int[] img;
    int[] seq;
    int[] right_img_id = {R.drawable.right_1,R.drawable.right_2,R.drawable.right_3,R.drawable.right_5,R.drawable.right_6,R.drawable.right_7,R.drawable.right_8,R.drawable.right_9,R.drawable.right_10,
            R.drawable.right_10,R.drawable.right_11,R.drawable.right_12,R.drawable.right_13,R.drawable.right_14,R.drawable.right_15};
    int score;
    int cnt;
    int pro;
    int[] left_img_id = {R.drawable.left_1,R.drawable.left_2,R.drawable.left_3,R.drawable.left_4,R.drawable.left_5,R.drawable.left_6,R.drawable.left_7,R.drawable.left_8,R.drawable.left_9,
            R.drawable.left_10,R.drawable.left_11,R.drawable.left_12,R.drawable.left_13,R.drawable.left_14,R.drawable.left_15};

    boolean isPlaying;
    ProgressBar pro_game4;

    // 최고 점수 용(볼리)
    String mem_id;
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request, request_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4);
        btn_game4_return = findViewById(R.id.btn_game4_return);
        cl_game4 = findViewById(R.id.cl_index);
        tv_game4_score = findViewById(R.id.tv_game4_score);
        tv_game4_indicate = findViewById(R.id.tv_game4_indicate);
        tv_game4_best = findViewById(R.id.tv_game4_best);
        pro_game4= findViewById(R.id.pro_game4);

        img_correct_num = new ImageView[6];
        img_game4_num = new ImageView[6];
        tv_game4_score.setText("0");
        int cnt =0;

        getBestScore();

        btn_game4_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game4.this, GameActivity.class);
                startActivity(intent);
            }
        });

        isPlaying = true;
        pro_game4.setIndeterminate(false);
        pro_game4.setProgress(180);



        TimerThread timerThread = new TimerThread();
        timerThread.start();

        for (int i = 0; i < img_game4_num.length; i++) {
            int img_id = getResources().getIdentifier("img_game4_" + (i + 1), "id", getPackageName());
            img_game4_num[i] = findViewById(img_id);
        }

        for(int i =0; i<img_correct_num.length; i++){
            int img_id = getResources().getIdentifier("img_correct_" + (i + 1), "id", getPackageName());
            img_correct_num[i] = findViewById(img_id);
            img_correct_num[i].setVisibility(View.INVISIBLE);
            if(img_correct_num[i]!=null){
                Log.d("null","아님"+i);
            }
        }

        right();




        btn_game4_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game4.this,GameActivity.class);
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
//        String url = "http://172.30.1.28:8000/mobile/gamescore";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    String best_score = json.getString("max4");

                    if (best_score.equals("null")) {
                        tv_game4_best.setText("0");
                    } else {
                        tv_game4_best.setText(best_score);
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

    private void makeRight(){
        for(int i=0; i<img_game4_num.length; i ++){
            img_game4_num[seq[i]].setTag("left");
            if(i==5){
                img_game4_num[seq[i]].setTag("right");
            }
        }
        tv_game4_indicate.setText("다음중 오른손을 클릭하세요.");
    }

    private void change_img_left(){
        for(int i =0; i<img_game4_num.length;i++){
            img_game4_num[i].setVisibility(View.VISIBLE);
            if(img_game4_num[i].getTag().toString().equals("left")){
                img_correct_num[i].setImageResource(R.drawable.oimage);
            }else if (img_game4_num[i].getTag().toString().equals("right")){
                img_correct_num[i].setImageResource(R.drawable.ximage);
            }
        }
    }

    private void change_img_right(){
        for(int i =0; i<img_game4_num.length;i++){
            img_game4_num[i].setVisibility(View.VISIBLE);
            if(img_game4_num[i].getTag().toString().equals("right")){
                img_correct_num[i].setImageResource(R.drawable.oimage);
            }else{
                img_correct_num[i].setImageResource(R.drawable.ximage);
            }
        }
    }


    private void left(){
        int[] seq = new int[6];
        int[] img = new int[14];


        for (int i = 0; i < seq.length; i++) {
            seq[i] = rd.nextInt(6);
            for (int j = 0; j < i; j++) {
                if (seq[i] == seq[j]) {
                    i--;
                    break;
                }
            }
        }

        for(int i =0; i<img.length; i++){
            img[i] = rd.nextInt(14);
            for (int j = 0; j < i; j++) {
                if (img[i] == img[j]) {
                    i--;
                    break;
                }
            }
        }
        tv_game4_indicate.setText("다음중 왼손을 클릭하세요.");

        for(int i=0; i<img_game4_num.length; i ++){
            img_game4_num[seq[i]].setTag("right");
            img_game4_num[seq[i]].setImageResource(right_img_id[img[i]]);
            if(i==5){
                img_game4_num[seq[i]].setTag("left");
                img_game4_num[seq[i]].setImageResource(left_img_id[img[i]]);
            }
            Log.d("img_seq_id",img_game4_num[seq[i]].getTag().toString());
        }

        for(int i=0; i < img_game4_num.length;i++){
            final int pos = i;
            img_game4_num[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cnt+=5;
                    if(img_game4_num[pos].getTag()=="left"){
                        score = Integer.parseInt(tv_game4_score.getText().toString());
                        tv_game4_score.setText((score+5)+"");
                    }
                    change_img_left();
                    if(cnt%5==0){
                        right();
                    }
                }
            });
        }
    }

    private void right(){
        int[] seq = new int[6];
        int[] img = new int[14];


        for (int i = 0; i < seq.length; i++) {
            seq[i] = rd.nextInt(6);
            for (int j = 0; j < i; j++) {
                if (seq[i] == seq[j]) {
                    i--;
                    break;
                }
            }
        }

        for(int i =0; i<img.length; i++){
            img[i] = rd.nextInt(14);
            for (int j = 0; j < i; j++) {
                if (img[i] == img[j]) {
                    i--;
                    break;
                }
            }
        }
        tv_game4_indicate.setText("다음중 오른손을 클릭하세요.");

        for(int i=0; i<img_game4_num.length; i ++){
            img_game4_num[seq[i]].setTag("left");
            img_game4_num[seq[i]].setImageResource(left_img_id[img[i]]);
            if(i==5){
                img_game4_num[seq[i]].setTag("right");
                img_game4_num[seq[i]].setImageResource(right_img_id[img[i]]);
            }
            Log.d("img_seq_id",img_game4_num[seq[i]].getTag().toString());
        }

        for(int i=0; i < img_game4_num.length;i++){
            final int pos = i;
            img_game4_num[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    change_img_right();
                    cnt+=5;
                    if(img_game4_num[pos].getTag()=="right"){
                        score = Integer.parseInt(tv_game4_score.getText().toString());
                        tv_game4_score.setText((score+5)+"");
                    }
                    if(cnt%5==0){
                        left();
                    }
                }
            });
        }
    }
    Handler timeHandler = new Handler(){
        public void handleMessage(@NonNull Message msg) {
            int time = msg.arg1;
            pro = msg.arg2;
            if(time>=0){
                pro_game4.setProgress(180-pro);
            }
            else{
                isPlaying=false;
                for(int i =0; i<6;i++){
                    img_game4_num[i].setClickable(false);
                }
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                mem_id = auto.getString("mem_id", mem_id);
                String data = mem_id;
                Log.d("mem_id2", data);
                String score = tv_game4_score.getText().toString();
                Log.d("now__________", tv_game4_score +"");
                String url = "http://10.0.2.2:8000/mobile/gamesave";
//                String url = "http://172.30.1.28:8000/mobile/gamesave";

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
                        params.put("game_score4", score);
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
}