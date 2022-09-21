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

    public foodAdapter(Context context,int food_list, ArrayList<foodVO> data) {
        this.context = context;
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
        TextView tv_food_t =  view.findViewById(R.id.tv_food_t);
        tv_food_t.setText(data.get(i).getTv_food_t());
        TextView tv_food_c = view.findViewById(R.id.tv_food_c);
        tv_food_c.setText(data.get(i).getTv_food_c());
        ImageView img_food = view.findViewById(R.id.img_column);
        img_food.setImageResource(data.get(i).getImg_food());

        return view;
    }


}