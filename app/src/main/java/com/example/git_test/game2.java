package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class game2 extends AppCompatActivity {

    TextView tv_count_5;
    Button btn_test;
    ImageView[] game2_img_num;
    int cnt_tag =0;
    int[] id = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.imgfood1,R.drawable.imgfood2,R.drawable.imgfood3,R.drawable.imgfood4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        tv_count_5 = findViewById(R.id.tv_count_5);
        btn_test = findViewById(R.id.btn_test);

        game2_img_num = new ImageView[16];

        for (int i = 0; i < game2_img_num.length; i++) {
            int btn_id = getResources().getIdentifier("game2_img_" + (i + 1), "id", getPackageName());
            game2_img_num[i] = findViewById(btn_id);

            final int pos = i ;
            game2_img_num[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeImage();
                Toast.makeText(game2.this, "test", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void makeImage() {
        Random rd = new Random();
        int[] tag = new int[8];
        int[] rdn = new int[8];

        for (int i = 0; i < tag.length; i++) {
            tag[i] = rd.nextInt(8) + 1 ;
            for (int j = 0; j < i; j++) {
                if (tag[i] == tag[j]) {
                    i--;
                    break;
                }
            }
        }

        for (int i =0; i<tag.length; i++){
            rdn[i] = rd.nextInt(8)+1;
            game2_img_num[rdn[i]].setTag(tag[i]);
            game2_img_num[rdn[i]+7].setTag(tag[i]);
            for (int j = 0; j < i; j++) {
                if (rdn[i] == rdn[j]) {
                    i--;
                    break;
                }
            }
        }

//        for(int i =0; i<rdn.length; i++){
//            Log.d(i+"rnd수",rdn[i]+"");
//        }

        for(int i = 0; i<game2_img_num.length; i++){
            try {
                    Log.d("이거다", Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))+"");

            }catch (Exception e){
                Log.d("널포인트",e+"");
            }
        }


//        for(int i =0; i<game2_img_num.length; i++){
//            if(game2_img_num[i]!=null){
//                if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==1){
//                    game2_img_num[i].setImageResource(R.drawable.imgfood1);
//                    Log.d("tag",game2_img_num[i].getTag()+"");
//                }else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==2){
//                    game2_img_num[i].setImageResource(R.drawable.imgfood2);
//                }
//                else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==3){
//                    game2_img_num[i].setImageResource(R.drawable.imgfood3);
//                }
//                else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==4){
//                    game2_img_num[i].setImageResource(R.drawable.imgfood4);
//                }
//                else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==5){
//                    game2_img_num[i].setImageResource(R.drawable.img1);
//                }
//                else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==6){
//                    game2_img_num[i].setImageResource(R.drawable.img2);
//                }
//                else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==7){
//                    game2_img_num[i].setImageResource(R.drawable.img3);
//                }
//                else if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==8){
//                    game2_img_num[i].setImageResource(R.drawable.img4);
//                }
//            }else{
//                Log.d("null","null값");
//            }
//        }

//        for(int i =1; i<9; i++){
//            if(Integer.parseInt(String.valueOf(game2_img_num[i].getTag()))==1) {
//                game2_img_num[i].setImageResource(R.drawable.imgfood1);
//            }
//        }

    }

}