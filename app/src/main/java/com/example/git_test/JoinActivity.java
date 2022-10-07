package com.example.git_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    Button bt_join;
    EditText ed_name,ed_id, ed_pw,ed_birth,ed_gender;
    RequestQueue requestQueue;
    StringRequest request;
    RadioGroup radio_join;
    String type;
    ConstraintLayout cl;
    Spinner spinner_join;
    String text;
    String select_item;
    boolean click_cl;
    boolean check;
    String data5 =null;

//    SimpleDateFormat joinDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        bt_join = findViewById(R.id.bt_join);
        ed_name = findViewById(R.id.ed_name);
        ed_id = findViewById(R.id.ed_id);
        ed_pw = findViewById(R.id.ed_pw);
        ed_birth = findViewById(R.id.ed_birth);
//        ed_gender = findViewById(R.id.ed_gender);
        radio_join = findViewById(R.id.radio_join);
        spinner_join = (Spinner) findViewById(R.id.spinner_join);
        cl = findViewById(R.id.cl);

        ArrayList<String> gender_array= new ArrayList<>();
        gender_array.add("남성");
        gender_array.add("여성");

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        spinner_join.setPrompt("성별을 선택해주세요.");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_join.setSelection(0);
        spinner_join.setAdapter(adapter);


        radio_join.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_client:
                        type="n";
                        break;
                    case R.id.radio_parent:
                        type="p";
                        break;
                    case R.id.radio_doctor:
                        type="d";
                        break;
                }
            }
        });

//        String l_url = "http://10.0.2.2:8000/mobile/regist";
        String l_url = "http://172.30.1.28:8000/mobile/regist";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItemName = spinner_join.getSelectedItem().toString();
                if(selectedItemName.equals("남성")){
                    data5 = "M";
                }else{
                    data5 = "F";
                }

                String data1 = ed_id.getText().toString();
                String data2 = ed_pw.getText().toString();
                String data3 = ed_name.getText().toString();
                String data4 = ed_birth.getText().toString();
//                String data5 = ed_gender.getText().toString();
                String data6 = type;

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
                                        Intent intent_res = new Intent(JoinActivity.this, IndexActivity.class);
                                        startActivity(intent_res);
                                    } else if (code.equals("400")) {
                                        Toast.makeText(getApplicationContext(), "중복된 아이디입니다. 다른 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else if (code.equals("500")){
                                        Toast.makeText(JoinActivity.this, "가입에 필요한 정보가 기입되지 않은 부분이 있습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "가입에 필요한 정보가 기입되지 않은 부분이 있습니다.", Toast.LENGTH_SHORT).show();
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

                        if(data1!=null&&data2!=null&&data3!=null&&data4!=null&&data5!=null&&data6!=null){
                            params.put("mem_id", data1);
                            params.put("mem_pw", data2);
                            params.put("mem_name", data3);
                            params.put("mem_birth", data4);
                            params.put("mem_gender", data5);
                            params.put("mem_type", data6);
                        }
                        return params;
                    }
                };
                request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                        20000 ,

                        com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                        com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(request);
            }
        });


            }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String Text = String.valueOf(spinner_join.getSelectedItem());
        Log.d("item",text);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}