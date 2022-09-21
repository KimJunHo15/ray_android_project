package com.example.git_test;

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

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class IndexActivity extends AppCompatActivity {

    Button btn_login, btn_join;
    EditText et_id, et_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
        Log.d("권한",permissionCheck+"");
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            //권한 없음
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                            startActivity(intent);
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
                            Toast.makeText(IndexActivity.this, "권한이 허가되지 않았습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            };

            TedPermission.with(this)
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("서비스 이용을 위해 갤러리, 카메라 및 인터넷 접근 권한이 필요합니다.")
                    .setDeniedMessage("서비스 이용을 위해서는 [설정] > [권한] 에서 권한을 허용해 주세요.")
                    .setPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET})
                    .check();
        }


    }
}