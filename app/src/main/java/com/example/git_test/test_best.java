package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class test_best extends AppCompatActivity {
    String mem_id;
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_best);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);
        String data = mem_id;
        String score = "20";
        String url = "http://10.0.2.2:8000/mobile/gamesave";

        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            String code = jsonObject.getString("code");
                            if(code.equals(200)){
//                                Toast.makeText(game1.this, "저장완료", Toast.LENGTH_SHORT).show();
                                Log.d("성공이다","성공");

                            }else{
                                Log.d("error실패다","실패");
                            }
                        } catch (Exception e) {
//                            Toast.makeText(game1.this, "에러발생", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(game1.this, error + "", Toast.LENGTH_SHORT).show();
                        Log.d("error에러입니다",error.toString());
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
}