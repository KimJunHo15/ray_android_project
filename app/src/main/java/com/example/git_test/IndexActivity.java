package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexActivity extends AppCompatActivity {

    Button btn_login, btn_join;
    EditText et_id, et_pw;
    RequestQueue requestQueue;
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);

        //String l_url2 = "http://127.0.0.1:8000/m_login";
        String l_url = "http://10.0.2.2:8000/m_login";
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
        Log.d("권한", permissionCheck + "");
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            //권한 없음
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
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
                                                    Intent intent_login = new Intent(IndexActivity.this, MainActivity.class);
                                                    startActivity(intent_login);
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
                                            Toast.makeText(getApplicationContext(), "인터넷 권한을 확인해주세요.", Toast.LENGTH_SHORT).show();
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

                                    params.put("user_id", data);
                                    params.put("user_pw", data2);

                                    return params;
                                }
                            };
                            requestQueue.add(request);
                        }
                    });
                    btn_join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent_join = new Intent(MainActivity.class, JoinActivity.class);
//                            startActivity(intent_join);
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
}