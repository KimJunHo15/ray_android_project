package com.example.git_test;

import static com.example.git_test.Exam_upload.rotateBitmap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Exam_camera extends AppCompatActivity {

    ImageView img_camera_pre;
    Button btn_camera_send, btn_re_capture;
    private File photoFile;
    String id = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_camera);
        img_camera_pre = findViewById(R.id.img_camera_pre);
        btn_camera_send = findViewById(R.id.btn_camera_send);
        btn_re_capture = findViewById(R.id.btn_re_capture);

        SharedPreferences auto = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        SharedPreferences.Editor autoLoginEdit = auto.edit();
        id = auto.getString("mem_id", id);

        camera();

        btn_re_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
            }
        });

        btn_camera_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImage();
            }
        });
    }
    private void camera(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(i.resolveActivity(Exam_camera.this.getPackageManager())!=null){
            // 사진의 파일명을 만들기
            String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            photoFile = getPhotoFile(fileName);

            Uri fileProvider = FileProvider.getUriForFile(Exam_camera.this,
                    "com.example.git_test.fileprovider", photoFile);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
            startActivityForResult(i, 100);
        }else{
            Toast.makeText(Exam_camera.this, "사용할 수 있는 카메라 앱이 없습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private File getPhotoFile(String fileName){
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try{
            return File.createTempFile(fileName, ".jpg", storageDirectory);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void displayFileChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), 300);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && resultCode == RESULT_OK){

            Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(photoFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            photo = rotateBitmap(photo, orientation);

            // 압축시킨다. 해상도 낮춰서
            OutputStream os;
            try {
                os = new FileOutputStream(photoFile);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            img_camera_pre.setImageBitmap(photo);
            img_camera_pre.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            // 네트워크로 데이터 보낸다.


        }else if(requestCode == 300 && resultCode == RESULT_OK && data != null &&
                data.getData() != null){

            Uri albumUri = data.getData( );
            String fileName = getFileName( albumUri );
            try {

                ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( albumUri, "r" );
                if ( parcelFileDescriptor == null ) return;
                FileInputStream inputStream = new FileInputStream( parcelFileDescriptor.getFileDescriptor( ) );
                photoFile = new File( this.getCacheDir( ), fileName );
                FileOutputStream outputStream = new FileOutputStream( photoFile );
                IOUtils.copy( inputStream, outputStream );

//                //임시파일 생성
//                File file = createImgCacheFile( );
//                String cacheFilePath = file.getAbsolutePath( );


                // 압축시킨다. 해상도 낮춰서
                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                OutputStream os;
                try {
                    os = new FileOutputStream(photoFile);
                    photo.compress(Bitmap.CompressFormat.JPEG, 60, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                img_camera_pre.setImageBitmap(photo);
                img_camera_pre.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

//                imageView.setImageBitmap( getBitmapAlbum( imageView, albumUri ) );

            } catch ( Exception e ) {
                e.printStackTrace( );
            }
            // 네트워크로 보낸다.
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFileName( Uri uri ) {
        Cursor cursor = getContentResolver( ).query( uri, null, null, null, null );
        try {
            if ( cursor == null ) return null;
            cursor.moveToFirst( );
            @SuppressLint("Range") String fileName = cursor.getString( cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME ) );
            cursor.close( );
            return fileName;

        } catch ( Exception e ) {
            e.printStackTrace( );
            cursor.close( );
            return null;
        }
    }

    public Bitmap getBitmapAlbum(View targetView,Uri uri){
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( uri, "r" );
            if ( parcelFileDescriptor == null ) return null;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor( );
            if ( fileDescriptor == null ) return null;

            int targetW = targetView.getWidth( );
            int targetH = targetView.getHeight( );

            BitmapFactory.Options options = new BitmapFactory.Options( );
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFileDescriptor( fileDescriptor, null, options );

            int photoW = options.outWidth;
            int photoH = options.outHeight;

            int scaleFactor = Math.min( photoW / targetW, photoH / targetH );
            if ( scaleFactor >= 8 ) {
                options.inSampleSize = 8;
            } else if ( scaleFactor >= 4 ) {
                options.inSampleSize = 4;
            } else {
                options.inSampleSize = 2;
            }
            options.inJustDecodeBounds = false;

            Bitmap reSizeBit = BitmapFactory.decodeFileDescriptor( fileDescriptor, null, options );

            ExifInterface exifInterface = null;
            try {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
                    exifInterface = new ExifInterface( fileDescriptor );
                }
            } catch ( IOException e ) {
                e.printStackTrace( );
            }

            int exifOrientation;
            int exifDegree = 0;

            //사진 회전값 구하기
            if ( exifInterface != null ) {
                exifOrientation = exifInterface.getAttributeInt( ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL );

                if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_90 ) {
                    exifDegree = 90;
                } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_180 ) {
                    exifDegree = 180;
                } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_270 ) {
                    exifDegree = 270;
                }
            }

            parcelFileDescriptor.close( );
            Matrix matrix = new Matrix( );
            matrix.postRotate( exifDegree );

            Bitmap reSizeExifBitmap = Bitmap.createBitmap( reSizeBit, 0, 0, reSizeBit.getWidth( ), reSizeBit.getHeight( ), matrix, true );
            return reSizeExifBitmap;

        } catch ( Exception e ) {
            e.printStackTrace( );
            return null;
        }
    }

    private void sendImage(){
        Retrofit retrofit = NetworkClient.getRetrofitClient(Exam_camera.this);
        PostingApi api = retrofit.create(PostingApi.class);

// 멀티파트로 파일을 보내는 경우, 파일 파라미터 만드는 방법
        RequestBody fileBody = RequestBody.create(photoFile, MediaType.parse("image/*"));
// 키값, 파일명, 실제 파일
        MultipartBody.Part exam_img = MultipartBody.Part.createFormData("exam_img",
                photoFile.getName(), fileBody);

// 멀티파트로 form-data형식 text 보내는 경우
        MultipartBody.Part mem_id = MultipartBody.Part.createFormData("mem_id", id);

        Call<PostingRes> call = api.addPosting(mem_id, exam_img);


        call.enqueue(new Callback<PostingRes>() {
            @Override
            public void onResponse(Call<PostingRes> call, Response<PostingRes> response) {

                if(response.isSuccessful()){
                    Toast.makeText(Exam_camera.this, "포스팅 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(Exam_camera.this, "에러발생 : " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostingRes> call, Throwable t) {

                // 네트워크 자체 문제로 실패
                Toast.makeText(Exam_camera.this, "네트워크에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}