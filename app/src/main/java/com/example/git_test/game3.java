package com.example.git_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class game3 extends AppCompatActivity {

    Random rd = new Random();

    TextView tv_game3_best, tv_game3_score;
    Button btn_return_game3;
    ProgressBar pro_game3;
    ImageView[] game3_img_num;
    int turn;
    int cnt;
    int pro;
    int score;
    String equal;
    boolean turning;
    boolean isPlaying;

    String mem_id = "";
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request;


    ArrayList<ImageView> game3_img_num_array = new ArrayList<>();
    ArrayList<String> save_tag = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);

        btn_return_game3 = findViewById(R.id.btn_return_game3);
        pro_game3=findViewById(R.id.pro_game3);
        tv_game3_best = findViewById(R.id.tv_game4_best);
        tv_game3_score = findViewById(R.id.tv_game3_score);

        btn_return_game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game3.this,GameActivity.class);
                startActivity(intent);
            }
        });
        cnt=1;
        score = 0;

        isPlaying = true;
        pro_game3.setIndeterminate(false);
        pro_game3.setProgress(180);

        TimerThread timerThread = new TimerThread();
        timerThread.start();


        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);

        game3_img_num = new ImageView[9];

        String url = "http://10.0.2.2:8000/mobile/showmember";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue_img = Volley.newRequestQueue(getApplicationContext());


//        for (int i = 0; i < game3_img_num.length; i++) {
//            int img_id = getResources().getIdentifier("game3_img_" + (i + 1), "id", getPackageName());
//            game3_img_num[i] = findViewById(img_id);
//        }
//
//        for (int i = 0; i < game3_img_num.length; i++) {
//            game3_img_num_array.add(game3_img_num[i]);
//        }

        for(int i=0; i<game3_img_num.length; i++ ){
            int img_id = getResources().getIdentifier("game3_img_" + (i + 1), "id", getPackageName());
            game3_img_num_array.add(findViewById(img_id));
        }

        game3_action();

//        for(int i =0;i<game3_img_num_array.size();i++){
//
//            final int pos = i;
//            game3_img_num_array.get(pos).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(game3_img_num_array.get(pos).getTag().toString().equals(String.valueOf(1))&&cnt<=turn){
//                        int i1 = score+5;
//                        tv_game3_score.setText(String.valueOf(i1));
//                        cnt+=1;
//                        game3_img_num_array.get(pos).setVisibility(View.INVISIBLE);
//                    }else{
//                        game3_action();
//                    }
//                    if(game3_img_num_array.get(pos).getTag().toString().equals(String.valueOf(2))&&cnt<=turn){
//                        int i1 = score+5;
//                        tv_game3_score.setText(String.valueOf(i1));
//                        cnt+=1;
//                        game3_img_num_array.get(pos).setVisibility(View.INVISIBLE);
//                    }else{
//                        game3_action();
//                    }
//                }
//            });
//        }
    }

    private void game3_action(){
        makeTag();
        Collections.shuffle(game3_img_num_array);

        turn = rd.nextInt(6) + 2;

        for (int i = 1; i <= turn; i++) {
            for (int j = 0; j < game3_img_num_array.size(); j++) {
                equal = String.valueOf(i);
                Log.d("equal", equal);
                if (game3_img_num_array.get(j).getTag().toString().equals(equal)) {
                    turn_card(j);
                    return_card(j);
                    save_tag.add(game3_img_num_array.get(j).getTag().toString());
                }
            }
        }
    }

    private void turn_card(int pos) {
        setImageChange(pos);
        Log.d("tag", game3_img_num_array.get(pos).getTag().toString());
    }

    private void return_card(int pos) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                game3_img_num_array.get(pos).setImageResource(R.drawable.big_logo);
            }
        }, 1000);
    }

    public void makeTag() {
        Random rd = new Random();
        int[] tag = new int[9];
        for (int i = 0; i < tag.length; i++) {
            tag[i] = rd.nextInt(9) + 1;
            for (int j = 0; j < i; j++) {
                if (tag[i] == tag[j]) {
                    i--;
                    break;
                }
            }
        }

//        for (int i = 0; i < game3_img_num.length; i++) {
//            game3_img_num[i].setTag(tag[i]);
//        }
        for(int i=0; i<game3_img_num_array.size();i++){
            game3_img_num_array.get(i).setTag(tag[i]);
        }
    }

    private void setImageChange(int pos) {
        if (game3_img_num_array.get(pos) != null) {
            if (game3_img_num_array.get(pos).getTag().toString().equals("1")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.red);
            } else if (game3_img_num_array.get(pos).getTag().toString().equals("2")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.orange);
            } else if (game3_img_num_array.get(pos).getTag().toString().equals("3")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.yellow);
            } else if (game3_img_num_array.get(pos).getTag().toString().equals("4")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.green);
            } else if (game3_img_num_array.get(pos).getTag().toString().equals("5")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.blue);
            } else if (game3_img_num_array.get(pos).getTag().toString().equals("6")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.indigo);
            } else if (game3_img_num_array.get(pos).getTag().toString().equals("7")) {
                game3_img_num_array.get(pos).setImageResource(R.drawable.violet);
            }
        }
    }

    class turnThread extends Thread{
        @Override
        public void run() {
            while(turning);

        }
    }
    Handler timeHandler = new Handler(){
        public void handleMessage(@NonNull Message msg) {
            int time = msg.arg1;
            pro = msg.arg2;
            if(time>=0){
                int time_m = time/60;
                int time_s = time%60;
//                tv_game4_timer.setText("남은시간"+ time_m+" : "+time_s);
                pro_game3.setProgress(180-pro);
            }
            else{
//                tv_game4_timer.setText("게임종료");
                isPlaying=false;
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