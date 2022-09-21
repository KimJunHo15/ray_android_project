package com.example.git_test.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.git_test.R;

import java.util.ArrayList;

public class foodAdapter extends BaseAdapter {
    Context context;
    int food_list;
    ArrayList<foodVO> data;
    LayoutInflater inflater;

    public foodAdapter(int column_list, ArrayList<foodVO> data) {
        this.food_list = food_list;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=inflater.inflate(food_list,viewGroup,false);
        }
        ImageView img_food = view.findViewById(R.id.img_food);
        img_food.setImageResource(data.get(i).getImg_food());

        return view;
    }


}