package com.example.git_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class game4 extends AppCompatActivity {

    Random rd = new Random();


    Button btn_game4_return;
    ConstraintLayout cl_game4;
    ImageView[] img_game4_num;
    ImageView[] img_correct_num;
    TextView tv_game4_count,tv_game4_indicate;
    int[] img;
    int[] seq;
    int[] right_img_id = {R.drawable.right_1,R.drawable.right_2,R.drawable.right_3,R.drawable.right_4,R.drawable.right_5,R.drawable.right_6,R.drawable.right_7,R.drawable.right_8,R.drawable.right_9,R.drawable.right_10};
    int score;
    int cnt;
    int[] left_img_id = {R.drawable.left_1,R.drawable.left_2,R.drawable.left_3,R.drawable.left_4,R.drawable.left_5,R.drawable.left_6,R.drawable.left_7,R.drawable.left_8,R.drawable.left_9,R.drawable.left_10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4);
        btn_game4_return = findViewById(R.id.btn_game4_return);
        cl_game4 = findViewById(R.id.cl_index);
        tv_game4_count = findViewById(R.id.tv_game4_score);
        tv_game4_indicate = findViewById(R.id.tv_game4_indicate);

        img_correct_num = new ImageView[6];
        img_game4_num = new ImageView[6];
        tv_game4_count.setText("0");
        int cnt =0;

        for (int i = 0; i < img_game4_num.length; i++) {
            int img_id = getResources().getIdentifier("img_game4_" + (i + 1), "id", getPackageName());
            img_game4_num[i] = findViewById(img_id);
        }


        for(int i =0; i<img_correct_num.length; i++){
            int img_id = getResources().getIdentifier("img_correct_" + (i + 1), "id", getPackageName());
            img_correct_num[i] = findViewById(img_id);
            img_correct_num[i].setVisibility(View.INVISIBLE);
            if(img_correct_num[i]!=null){
                Log.d("null","아님"+i);
            }
        }

        right();


        btn_game4_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });

    }

    private void makeRight(){
        for(int i=0; i<img_game4_num.length; i ++){
            img_game4_num[seq[i]].setTag("left");
            if(i==5){
                img_game4_num[seq[i]].setTag("right");
            }
        }
        tv_game4_indicate.setText("다음중 오른손을 클릭하세요.");
    }

    private void change_img_left(){
        for(int i =0; i<img_game4_num.length;i++){
            img_game4_num[i].setVisibility(View.VISIBLE);
            if(img_game4_num[i].getTag().toString().equals("left")){
                img_correct_num[i].setImageResource(R.drawable.oimage);
            }else{
                img_correct_num[i].setImageResource(R.drawable.ximage);
            }
        }
    }

    private void change_img_right(){
        for(int i =0; i<img_game4_num.length;i++){
            if(img_game4_num[i].getTag().toString().equals("right")){
                img_correct_num[i].setImageResource(R.drawable.oimage);
            }else{
                img_correct_num[i].setImageResource(R.drawable.ximage);
            }
        }
    }


    private void left(){
        int[] seq = new int[6];
        int[] img = new int[10];


        for (int i = 0; i < seq.length; i++) {
            seq[i] = rd.nextInt(6);
            for (int j = 0; j < i; j++) {
                if (seq[i] == seq[j]) {
                    i--;
                    break;
                }
            }
        }

        for(int i =0; i<img.length; i++){
            img[i] = rd.nextInt(10);
            for (int j = 0; j < i; j++) {
                if (img[i] == img[j]) {
                    i--;
                    break;
                }
            }
        }
        tv_game4_indicate.setText("다음중 왼솔을 클릭하세요.");

        for(int i=0; i<img_game4_num.length; i ++){
            img_game4_num[seq[i]].setTag("right");
            img_game4_num[seq[i]].setImageResource(right_img_id[img[i]]);
            if(i==5){
                img_game4_num[seq[i]].setTag("left");
                img_game4_num[seq[i]].setImageResource(left_img_id[img[i]]);
            }
            Log.d("img_seq_id",img_game4_num[seq[i]].getTag().toString());
        }

        for(int i=0; i < img_game4_num.length;i++){
            final int pos = i;
            img_game4_num[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    change_img_left();
                    cnt+=5;
                    if(img_game4_num[pos].getTag()=="left"){
                        score = Integer.parseInt(tv_game4_count.getText().toString());
                        tv_game4_count.setText((score+5)+"");
                    }
                    if(cnt%5==0){
                        right();
                    }
                }
            });
        }
    }

    private void right(){
        int[] seq = new int[6];
        int[] img = new int[10];


        for (int i = 0; i < seq.length; i++) {
            seq[i] = rd.nextInt(6);
            for (int j = 0; j < i; j++) {
                if (seq[i] == seq[j]) {
                    i--;
                    break;
                }
            }
        }

        for(int i =0; i<img.length; i++){
            img[i] = rd.nextInt(10);
            for (int j = 0; j < i; j++) {
                if (img[i] == img[j]) {
                    i--;
                    break;
                }
            }
        }
        tv_game4_indicate.setText("다음중 오른손을 클릭하세요.");

        for(int i=0; i<img_game4_num.length; i ++){
            img_game4_num[seq[i]].setTag("left");
            img_game4_num[seq[i]].setImageResource(left_img_id[img[i]]);
            if(i==5){
                img_game4_num[seq[i]].setTag("right");
                img_game4_num[seq[i]].setImageResource(right_img_id[img[i]]);
            }
            Log.d("img_seq_id",img_game4_num[seq[i]].getTag().toString());
        }

        for(int i=0; i < img_game4_num.length;i++){
            final int pos = i;
            img_game4_num[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    change_img_right();
                    cnt+=5;
                    if(img_game4_num[pos].getTag()=="right"){
                        score = Integer.parseInt(tv_game4_count.getText().toString());
                        tv_game4_count.setText((score+5)+"");
                    }
                    if(cnt%5==0){
                        left();
                    }

                }
            });
        }
    }
}