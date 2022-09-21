package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    Button bt_join;
    EditText ed_name,ed_id, ed_pw,ed_birth,ed_gender;
    RequestQueue requestQueue;
    StringRequest request;
    RadioGroup radio_join;
    String type;
    SimpleDateFormat joinDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        bt_join = findViewById(R.id.bt_join);
        ed_name = findViewById(R.id.ed_name);
        ed_id = findViewById(R.id.et_id);
        ed_pw = findViewById(R.id.ed_pw);
        ed_birth = findViewById(R.id.ed_birth);
        ed_gender = findViewById(R.id.ed_gender);
        radio_join = findViewById(R.id.radio_join);
        joinDate= new SimpleDateFormat("yyyy-MM-dd");

        radio_join.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_client:
                        type="n";
                        Toast.makeText(JoinActivity.this, "type:"+type, Toast.LENGTH_SHORT).show();
                    case R.id.radio_parent:
                        type="p";
                        Toast.makeText(JoinActivity.this, "type:"+type, Toast.LENGTH_SHORT).show();
                    case R.id.radio_doctor:
                        type="d";
                        Toast.makeText(JoinActivity.this, "type:"+type, Toast.LENGTH_SHORT).show();
                }
            }
        });

        String l_url = "http://10.0.2.2:8000/m_regist";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data1 = ed_id.getText().toString();
                String data2 = ed_pw.getText().toString();
                String data3 = ed_name.getText().toString();
                String data4 = ed_birth.getText().toString();
                String data5 = ed_gender.getText().toString();
                String data6 = joinDate.toString();
                String data7 = type;


//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//                    @Override
//                    public void onResponse(String response) {
//                        try{
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if(success){
//                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(JoinActivity.this, IndexActivity.class );
//                                startActivity(intent);
//                               // finish();
//
//                            }else{
//                                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                        }
//                        catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
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
                                        Intent intent_Join = new Intent(JoinActivity.this, MainActivity.class);
                                        startActivity(intent_Join);
                                    } else if (code.equals("200")) {
                                        Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                        return;

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "서버 연결 에러.", Toast.LENGTH_SHORT).show();
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

                        params.put("user_id", data1);
                        params.put("user_pw", data2);
                        params.put("user_name", data3);
                        params.put("user_birth", data4);
                        params.put("user_gender", data5);
                        params.put("user_gender", data6);
                        params.put("user_type", data7);

                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });


            }
        }