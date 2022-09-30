package com.example.git_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class game3 extends AppCompatActivity {

    Random rd = new Random();

    Button btn_return_game3;
    ImageView[] game3_img_num;
    int time;
    int cnt;
    String equal;

    ArrayList<ImageView> game3_img_num_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);

        btn_return_game3 = findViewById(R.id.btn_return_game3);

        game3_img_num = new ImageView[9];
        time = rd.nextInt(7) + 2;

        for (int i = 0; i < game3_img_num.length; i++) {
            int img_id = getResources().getIdentifier("game3_img_" + (i + 1), "id", getPackageName());
            game3_img_num[i] = findViewById(img_id);
        }

        makeTag();

        for (int i = 0; i < game3_img_num.length; i++) {
            game3_img_num_array.add(game3_img_num[i]);
        }
        Collections.shuffle(game3_img_num_array);

        for (int i = 1; i <= time; i++) {
            equal = String.valueOf(i);
            for (int j = 0; j < game3_img_num_array.size(); j++) {
                if (game3_img_num_array.get(j).getTag().toString().equals(equal)) {
                    game3_img_num_array.get(j).setImageResource(R.drawable.img4_2);
                    Log.d(i + "tag", game3_img_num_array.get(j).getTag().toString());
                    final int pos = j;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                game3_img_num_array.get(pos).setImageResource(R.drawable.big_logo);
                        }
                    }, 1000);
                    break;
                }
            }
        }
    }

    private void makeTag() {
        Random rd = new Random();
        int[] tag = new int[9];
//        pos = new int[9];
//
//        for (int i = 0; i < game3_img_num.length; i++) {
//            pos[i] = rd.nextInt(9);
//            for (int j = 0; j < i; j++) {
//                if (pos[i] == pos[j]) {
//                    i--;
//                    break;
//                }
//            }
//        }
        for (int i = 0; i < tag.length; i++) {
            tag[i] = rd.nextInt(9) + 1;
            for (int j = 0; j < i; j++) {
                if (tag[i] == tag[j]) {
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < game3_img_num.length; i++) {
            game3_img_num[i].setTag(tag[i]);
        }
    }

    private void setImageAnswer(int pos) {

        if (game3_img_num[pos] != null) {
            if (game3_img_num[pos].getTag().toString().equals("1")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood1);
            } else if (game3_img_num[pos].getTag().toString().equals("2")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood2_2);
            } else if (game3_img_num[pos].getTag().toString().equals("3")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood3_2);
            } else if (game3_img_num[pos].getTag().toString().equals("4")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood4_2);
            } else if (game3_img_num[pos].getTag().toString().equals("5")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood5_2);
            } else if (game3_img_num[pos].getTag().toString().equals("6")) {
                game3_img_num[pos].setImageResource(R.drawable.imgfood6_2);
            } else if (game3_img_num[pos].getTag().toString().equals("7")) {
                game3_img_num[pos].setImageResource(R.drawable.img3_2);
            } else if (game3_img_num[pos].getTag().toString().equals("8")) {
                game3_img_num[pos].setImageResource(R.drawable.img4_2);
            } else if (game3_img_num[pos].getTag().toString().equals("9")) {
                game3_img_num[pos].setImageResource(R.drawable.img2);
            }
        }
    }

}