package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    Button bt_join;
    EditText ed_name,ed_id, ed_pw,ed_birth,ed_gender;
    RequestQueue requestQueue;
    StringRequest request;

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

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = ed_name.getText().toString();
                String data2 = ed_id.getText().toString();
                String data3 = ed_pw.getText().toString();
                String data4 = ed_birth.getText().toString();
                String data5 = ed_gender.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(JoinActivity.this, IndexActivity.class );
                                startActivity(intent);
                               // finish();

                            }else{
                                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };



            }
        });



    }
}