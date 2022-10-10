package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.bumptech.glide.Glide;
import com.example.git_test.Model.Data;
import com.example.git_test.Model.MyVO;
//import com.example.git_test.Model.Myadapter;
import com.example.git_test.Model.RecyclerAdaper;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Url;

public class MyActivity extends AppCompatActivity {

    ImageView img_return_m, img_test;
    TextView tv_name, tv_birth, tv_gender;
    RecyclerView rv;
    Button btn_logout;
    ConstraintLayout cl_my;
    boolean click_my;

    String mem_id = "";
    RequestQueue requestQueue, requestQueue_img;
    StringRequest request;
    StringRequest request_img;

    ArrayList<MyVO> myVO = new ArrayList<MyVO>();

    Bitmap bitmap;
    private RecyclerAdaper adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        img_return_m = findViewById(R.id.img_return_m);
        tv_name = findViewById(R.id.tv_name);
        tv_birth = findViewById(R.id.tv_birth);
        tv_gender = findViewById(R.id.tv_gender);
        btn_logout = findViewById(R.id.btn_logout);
        rv = findViewById(R.id.rv);
        cl_my = findViewById(R.id.cl_my);

        tv_name.setText("-");
        tv_gender.setText("-");
        tv_birth.setText("-");

        String image_url = "http://10.0.2.2:8000/media/IMG_20220920_074307_8Qyi4F5.jpg";

        init();


        // 현재 사용자 아이디 가지고오기
        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        mem_id = auto.getString("mem_id", mem_id);


        String data = mem_id;

        // 데이터 송수신용 서버
        String url = "http://10.0.2.2:8000/mobile/showmember";
//        String url = "http://172.30.1.28:8000/mobile/showmember";
        String url_img = "http://10.0.2.2:8000/mobile/scoredata";
//        String url_img = "http://172.30.1.28:8000/mobile/scoredata";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue_img = Volley.newRequestQueue(getApplicationContext());

        // 아이디에 맞는 회원 정보 보여주기
        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);

                            String mem_birth = json.getString("mem_birth");
                            String mem_gender = json.getString("mem_gender");
                            String mem_name = json.getString("mem_name");
                            Log.d("mem_name", mem_name);

                            tv_name.setText(mem_name);
                            tv_birth.setText(mem_birth);
                            tv_gender.setText(mem_gender);


                        } catch (Exception e) {
                            Toast.makeText(MyActivity.this, "에러발생", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyActivity.this, error + "", Toast.LENGTH_SHORT).show();
                        Log.d("error_info", error.toString());
                    }
                }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("mem_id", data);
                return params;
            }
        };
        requestQueue.add(request);

        // 이미지, 결과, 검사 시간 받아오기

        request_img = new StringRequest(
                Request.Method.POST,
                url_img,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);

                            if (json != null) {
                                String imgurl = json.getString("imgurl");
                                Log.d("imgurl2", imgurl);
                                String score = json.getString("score");
                                Log.d("score2", score);
                                String date = json.getString("date");
                                Log.d("date", date);
                                String plus = "http://10.0.2.2:8000/" + imgurl;
                                getData(score, date, plus);
                            }

                        } catch (Exception e) {
                            Toast.makeText(MyActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error_img", error.toString());
                    }
                }

        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("mem_id", data);
                return params;
            }
        };
        requestQueue_img.add(request_img);

        cl_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click_my == true) {
                    showSystemUI();
                    click_my = false;
                } else {
                    hideSystemUI();
                    click_my = true;
                }
            }
        });

        // 메인메뉴로 돌아가기
        img_return_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 로그아웃 기능
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLoginEdit = auto.edit();

                autoLoginEdit.clear();
                autoLoginEdit.commit();

                Intent return_intent = new Intent(MyActivity.this, IndexActivity.class);
                startActivity(return_intent);

//                ((IndexActivity)IndexActivity.iContext).autologin_logout();
//                SharedPreferences SharedPreferences = getSharedPreferences("logout", MyActivity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = SharedPreferences.edit();
//
//                editor.clear();
//                editor.commit();
                finish();
            }
        });
    }


    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdaper();
        rv.setAdapter(adapter);
    }

    private void getData(String score, String date, String url) {
        List<String> listScore = Arrays.asList(score);
        List<String> listDate = Arrays.asList(date);
        List<String> listUrl = Arrays.asList(url);

        for (int i = 0; i < listScore.size(); i++) {
            Data data = new Data();
            data.setScore(listScore.get(i));
            data.setDate(listDate.get(i));
            data.setImgurl(listUrl.get(i));

            adapter.additem(data);
        }
        adapter.notifyDataSetChanged();

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

