package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class ExamActivity extends AppCompatActivity {

    ImageView img_return_e, img_camera, img_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        img_return_e = findViewById(R.id.img_return_e);
        img_camera = findViewById(R.id.img_camera);
        img_upload = findViewById(R.id.img_upload);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
        Log.d("권한",permissionCheck+"");
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            //권한 없음
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                    img_camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ExamActivity.this, Exam_camera.class);
                            startActivity(intent);

                        }
                    });
                    img_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ExamActivity.this, Exam_upload.class);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(ExamActivity.this, "권한 거부\n 서비스 이용을 위해서는 권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                    img_camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(ExamActivity.this, "권한이 허가되지 않았습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
                    img_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(ExamActivity.this, "권한이 허가되지 않았습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            };

            TedPermission.with(this)
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("서비스 이용을 위해 갤러리, 카메라 접근 권한이 필요합니다.")
                    .setDeniedMessage("서비스 이용을 위해서는 [설정] > [권한] 에서 권한을 허용해 주세요.")
                    .setPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA})
                    .check();
        }


        img_return_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExamActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}