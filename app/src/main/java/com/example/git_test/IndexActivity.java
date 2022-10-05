package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexActivity extends AppCompatActivity {

    public static Context iContext;

    Button btn_login, btn_join;
    EditText et_id, et_pw;
    RequestQueue requestQueue;
    StringRequest request;
    ConstraintLayout cl_index;
    CheckBox autologin;
    SharedPreferences auto;
    String mem_id, mem_pw;
 //   boolean check = false;
    boolean click_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        iContext = this;

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        cl_index = findViewById(R.id.cl_index);
        autologin = findViewById(R.id.autologin);
        autologin.setVisibility(View.INVISIBLE);
        auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);

//        mem_id = auto.getString("mem_id",null);
//        mem_pw = auto.getString("mem_pw",null);

        

        String l_url2 = "http://127.0.0.1:8000/m_login";
       // String l_url = "http://10.0.2.2:8000/mobile/login";
        String l_url = "http://172.30.1.81:8000/mobile/login";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //자동 로그인 기능


        autologin_method();


        
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
        Log.d("권한", permissionCheck + "");
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            //권한 없음
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    
                    // 키보드 외 부분 클릭시 키보드 내려가는 기능
                    cl_index.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    });
                    cl_index.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (click_index ==true){
                                showSystemUI();
                                click_index = false;
                            }
                            else{
                                hideSystemUI();
                                click_index = true;
                            }
                        }
                    });


                    // 로그인 기능
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(IndexActivity.this, "권한이 허가되지 않았습니다.", Toast.LENGTH_LONG).show();
                            String data = et_id.getText().toString();
                            String data2 = et_pw.getText().toString();

                            request = new StringRequest(
                                    Request.Method.POST,
                                    l_url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject json = new JSONObject(response);
                                                String code = json.getString("code");
                                                if (code.equals("200")) {
//                                                    if(autologin.isChecked()){
//                                                        check = true;
//                                                        Log.d("boolean", String.valueOf(check));
//                                                        SharedPreferences.Editor autoLoginEdit = auto.edit();
//                                                        autoLoginEdit.putString("mem_id", data);
//                                                        autoLoginEdit.putString("mem_pw", data2);
//                                                        autoLoginEdit.commit();
//                                                    }
//                                                    else{
                                                        auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
                                                        SharedPreferences.Editor autoLoginEdit = auto.edit();
                                                        autoLoginEdit.putString("mem_id", data);
                                                        autoLoginEdit.putString("mem_pw", data2);
                                                        autoLoginEdit.commit();
//                                                    }

                                                    Intent intent_login = new Intent(IndexActivity.this, MainActivity.class);
                                                    startActivity(intent_login);
                                                    finish();

                                                } else if (code.equals("400")) {
                                                    Toast.makeText(IndexActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                                } else if (code.equals("500")) {
                                                    Toast.makeText(IndexActivity.this, "해당 ID의 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "서버연결 오류.", Toast.LENGTH_SHORT).show();
                                            Log.d("error에러", error.toString());
                                        }
                                    }
                            ) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    // map이 hashmap에서 나왔기 때문에 그럼, 하나의 식으로 외워
                                    // Map<key, value> 구조
                                    Map<String, String> params = new HashMap<>();

                                    params.put("mem_id", data);
                                    params.put("mem_pw", data2);

                                    return params;
                                }
                            };
                            request.setRetryPolicy(new RetryPolicy() {
                                @Override
                                public int getCurrentTimeout() {
                                    return 50000;
                                }

                                @Override
                                public int getCurrentRetryCount() {
                                    return 50000;
                                }

                                @Override
                                public void retry(VolleyError error) throws VolleyError {

                                }
                            });
                            requestQueue.add(request);
                        }
                    });
                btn_join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent_join = new Intent(IndexActivity.this, JoinActivity.class);
                        startActivity(intent_join);
                    }
                });
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(IndexActivity.this, "권한 거부\n 서비스 이용을 위해서는 권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                    btn_join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(IndexActivity.this, "권한이 허가되지 않았습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(IndexActivity.this, "권한이 허가되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };

            TedPermission.with(this)
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("서비스 이용을 위해 갤러리, 카메라 및 인터넷 접근 권한이 필요합니다.")
                    .setDeniedMessage("서비스 이용을 위해서는 [설정] > [권한] 에서 권한을 허용해 주세요.")
                    .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET})
                    .check();
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


    public void autologin_method(){
//        SharedPreferences auto = getSharedPreferences("autologin",MODE_PRIVATE);

        if(auto!=null){
            mem_id = auto.getString("mem_id",mem_id);
            mem_pw = auto.getString("mem_pw",mem_pw);
            if(mem_id!=null && mem_pw!=null){
                Intent intent_login = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(intent_login);
                finish();
            }
        }
    }
 //   public void autologin_logout(){
//        mem_id = null;
//        mem_pw = null;
//        SharedPreferences.Editor editor = SharedPreferences.edit();
    }
