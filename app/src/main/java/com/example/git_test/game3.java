package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
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

    Button btn_return_game3;
    ImageView[] game3_img_num;
    int turn;
    int cnt;
    String equal;
    boolean turning;

    String mem_id = "";
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request;


    ArrayList<ImageView> game3_img_num_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);

        btn_return_game3 = findViewById(R.id.btn_return_game3);

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);

        game3_img_num = new ImageView[9];
        turn = rd.nextInt(7) + 2;

        String url = "http://10.0.2.2:8000/mobile/showmember";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue_img = Volley.newRequestQueue(getApplicationContext());

//        request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject json = new JSONObject(response);
//
//                            String mem_birth = json.getString("mem_birth");
//                            String mem_gender = json.getString("mem_gender");
//                            String mem_name = json.getString("mem_name");
//
//
//                        } catch (Exception e) {
//                            Toast.makeText(MyActivity.this, "에러발생", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MyActivity.this, error + "", Toast.LENGTH_SHORT).show();
//                        Log.d("error_info",error.toString());
//                    }
//                }
//
//        ) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//
//                params.put("mem_id", data);
//                return params;
//            }
//        };
//        requestQueue.add(request);


        for (int i = 0; i < game3_img_num.length; i++) {
            int img_id = getResources().getIdentifier("game3_img_" + (i + 1), "id", getPackageName());
            game3_img_num[i] = findViewById(img_id);
        }

        makeTag();

        for (int i = 0; i < game3_img_num.length; i++) {
            game3_img_num_array.add(game3_img_num[i]);
        }
        Collections.shuffle(game3_img_num_array);

        for (int i = 0; i < game3_img_num.length; i++) {
            Log.d("이건 태그야~", game3_img_num[i].getTag().toString());
        }

        for (int i = 0; i < game3_img_num_array.size(); i++) {
            Log.d("이건 태그2야~", game3_img_num_array.get(i).getTag().toString());
        }


        for (int i = 1; i <= turn; i++) {
            equal = String.valueOf(i);
            Log.d("equal", equal);
            for (int j = 0; j < game3_img_num_array.size(); j++) {
                if (game3_img_num_array.get(j).getTag().toString().equals(equal)) {
                    turn_card(j);
                    return_card(j);
                }
            }
        }
    }

    private void turn_card(int pos) {
        game3_img_num_array.get(pos).setImageResource(R.drawable.img4_2);
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

        for (int i = 0; i < game3_img_num.length; i++) {
            game3_img_num[i].setTag(tag[i]);
        }
    }

    private void setImageAnswer(int pos) {

        if (game3_img_num[pos] != null) {
            if (game3_img_num[pos].getTag().toString().equals("1")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood1);
            } else if (game3_img_num[pos].getTag().toString().equals("2")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood2_2);
            } else if (game3_img_num[pos].getTag().toString().equals("3")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood3_2);
            } else if (game3_img_num[pos].getTag().toString().equals("4")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood4_2);
            } else if (game3_img_num[pos].getTag().toString().equals("5")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood5_2);
            } else if (game3_img_num[pos].getTag().toString().equals("6")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood6_2);
            } else if (game3_img_num[pos].getTag().toString().equals("7")) {
                game3_img_num[pos].setImageResource(R.drawable.img3_2);
            } else if (game3_img_num[pos].getTag().toString().equals("8")) {
                game3_img_num[pos].setImageResource(R.drawable.img4_2);
            } else if (game3_img_num[pos].getTag().toString().equals("9")) {
                game3_img_num[pos].setImageResource(R.drawable.img2);
            }
        }
    }

    class turnThread extends Thread{
        @Override
        public void run() {
            while(turning);

        }
    }
}